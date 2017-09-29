/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.core

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings
import akka.testkit.ImplicitSender
import akka.testkit.TestActorRef
import akka.testkit.TestKit
import akka.testkit.TestProbe
import com.stratio.tikitakka.columbus.consul.ConsulComponent
import com.stratio.tikitakka.columbus.test.utils.consul.AgentService
import com.stratio.tikitakka.columbus.test.utils.consul.ConsulUtils
import com.stratio.tikitakka.columbus.test.utils.consul.UnregisterService
import com.stratio.tikitakka.common.message.AppsDiscovered
import com.stratio.tikitakka.common.message.DiscoverServices
import com.stratio.tikitakka.common.util.ConfigComponent
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterAll
import org.scalatest.ShouldMatchers
import org.scalatest.WordSpecLike
import org.scalatest.junit.JUnitRunner
import scala.concurrent.duration._

import scala.concurrent.Await

@RunWith(classOf[JUnitRunner])
class DiscoveryActorIT extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with WordSpecLike
  with ShouldMatchers
  with BeforeAndAfterAll
  with ConsulUtils{

  implicit val testSystem = ActorSystem("test")
  implicit val actorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))
  implicit val uri = ConfigComponent.config.getString(ConsulComponent.uriField)

  val services = (0 to 4).map(_ => AgentService.randomObject.copy(Tags = List[String]("theTag")))
  val servicesMap = services.map(s => s.Service -> s.Tags)
  val datacenter = Await.result(getDatacenter, 3 seconds)
  val nodeCatalog = Await.result(getNode, 3 seconds)
  val catalogServices = services.map { service => service.toCatalogService(datacenter, nodeCatalog)}
  val unregisterServiceModels = catalogServices.map { c => UnregisterService(c.Datacenter, c.Node, c.Service.ID)}
  val timeout = 3 seconds

  override def beforeAll(): Unit = {
    Await.result(registerServices(catalogServices.toList), timeout)
  }

  "Discovery Actor" should {

    "Discover new services" in {

      val orchestratorActor = TestProbe()
      val service = ConsulComponent(system, actorMaterializer)
      val servicesActor = TestActorRef[ServicesActor](new ServicesActor(orchestratorActor.ref))
      val discoveryActor = TestActorRef[DiscoveryActor](new DiscoveryActor(service, servicesActor))

      discoveryActor ! DiscoverServices(List("theTag"))
      expectMsg(AppsDiscovered(servicesMap.toMap))
      Thread.sleep(1000)
      servicesActor.underlyingActor.services.keys should contain theSameElementsAs servicesMap.toMap.keySet
      servicesMap.toMap.keySet.foreach(k => servicesActor.underlyingActor.context.child(k) should not be None)
    }
  }

  override def afterAll(): Unit = {
    Await.result(unregisterServices(unregisterServiceModels.toList), timeout)
  }
}
