/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.core

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Cancellable
import akka.actor.Kill
import akka.actor.Props
import com.stratio.tikitakka.common.message.AppUnDiscovered
import com.stratio.tikitakka.common.message.AppsDiscovered
import com.stratio.tikitakka.common.message.ManageApp
import com.stratio.tikitakka.common.util.ConfigComponent

import scalaz.Reader
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ServicesActor(orchestrator: ActorRef) extends Actor with ActorLogging {

  var services = Map[String, Cancellable]()
  lazy val discoverInitialDelayInSeconds = ConfigComponent.getInt("discover.initial.delay.in.seconds", 5) second
  lazy val discoverIntervalInSeconds = ConfigComponent.getInt("discover.interval.in.seconds", 5) second

  override def receive: Receive = {

    case AppsDiscovered(appsDiscovered) => appsDiscovered.foreach{ app =>
      services.get(app._1) match {
        case Some(_) => log.debug(s"[${app._1}] already managed")
        case None =>
          val actorRef = context.actorOf(Props(classOf[ServiceActor], sender, orchestrator), app._1)
          val cancellable = context.system.scheduler.schedule(
            discoverInitialDelayInSeconds,
            discoverIntervalInSeconds,
            actorRef,
            ManageApp(app._1)
          )
          services = services + (app._1 -> cancellable)
      }
    }

    case AppUnDiscovered(appName) =>
      services.get(appName).map(_.cancel()).foreach(status => if(status) services = services - appName)
      context.child(appName).foreach(_ ! Kill)

    case _ => log.debug(s"Unknown message")
  }
}

object ServicesActor {

  def props = Reader {
    (dependencies: Dependencies) => Props(classOf[ServicesActor], dependencies.orchestratorActorRef)
  }
}
