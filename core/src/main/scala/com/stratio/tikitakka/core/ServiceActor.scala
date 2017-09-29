/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.core

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import com.stratio.tikitakka.common.message._
import com.stratio.tikitakka.common.model.discovery.DiscoveryAppInfo

import scalaz.Reader

class ServiceActor(discoveryActor: ActorRef, orchestrator: ActorRef) extends Actor with ActorLogging {

  var status: Option[DiscoveryAppInfo] = None
  var appName: String = "UNKNOWN"

  override def receive: Receive = {
    case ManageApp(name) =>
      appName = name
      log.debug(s"[$appName] trigger discovery check")
      discoveryActor ! DiscoverService(appName)
      context.become(ready)

    case _ => log.debug(s"Unknown message")
  }

  def ready: Receive = {
    case AppDiscovered(Some(appDiscovered)) =>
      log.info(s"[${appDiscovered.name}] discovered with id: ${appDiscovered.id}")

      if(Option(appDiscovered) != status) {
        status = Option(appDiscovered)
        orchestrator ! RegisterApplication(appDiscovered)
      } else {
        log.debug(s"[${appDiscovered.name} discovered status has no changes]")
      }

    case AppDiscovered(None) =>
      log.debug(s"[${status.map(_.name).getOrElse("UNKNOWN")}] not discovered sending message to killing myself")
      context.parent ! AppUnDiscovered(appName)
      status.foreach(st => orchestrator ! UnregisterApplication(st))

    case _ => log.debug(s"Unknown message")
  }

}

object ServiceActor {

  def props = Reader {
    (dependencies: Dependencies) =>
      Props(classOf[ServiceActor], dependencies.discoveryActorRef, dependencies.orchestratorActorRef)
  }

}
