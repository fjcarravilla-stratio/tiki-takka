/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.columbus.test.utils

import com.stratio.tikitakka.columbus.DiscoveryComponent
import com.stratio.tikitakka.common.model.discovery.DiscoveryAppInfo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait DummyDiscoveryComponent extends DiscoveryComponent {

  val uri: String

  val upHost = "upHost"
  val servicesDiscovered = Map.empty[String, List[String]]
  val serviceDiscovered: Option[DiscoveryAppInfo] = None

  def isUp = if (uri == upHost) Future(true) else Future(false)

  def discover(tags: List[String] = List.empty[String]): Future[Map[String, List[String]]] =
    Future(servicesDiscovered)

  def discover(serviceName: String): Future[Option[DiscoveryAppInfo]] = Future(serviceDiscovered)

}
