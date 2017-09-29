/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.columbus

import com.stratio.tikitakka.columbus.test.utils.DummyDiscoveryComponent

import scala.concurrent.Await
import scala.concurrent.duration._
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class DiscoveryComponentUnitTest extends WordSpec with ShouldMatchers {

  val timeout = 3 seconds

  "DiscoveryComponent" should {

    "know if the discovery service is up" in new DummyDiscoveryComponent {

      val uri = upHost

      Await.result(isUp, timeout) should be(true)

    }

    "know if the discovery service is down" in new DummyDiscoveryComponent {

      val uri = "fakeHost"

      Await.result(isUp, timeout) should be(false)

    }
  }

}
