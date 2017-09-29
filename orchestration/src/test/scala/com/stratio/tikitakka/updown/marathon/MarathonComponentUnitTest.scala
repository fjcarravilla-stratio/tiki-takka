/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.updown.marathon

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ShouldMatchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class MarathonComponentUnitTest extends WordSpec with ShouldMatchers {

  trait ActorTestSystem {
    implicit val system = ActorSystem("Actor-Test-System")
    implicit val actorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))
  }

  "Marathon Orchestrator" should {
    "recover the uri correctly" in new MarathonComponent with ActorTestSystem {
      uri shouldBe "http://localhost:8080"
    }
  }
}
