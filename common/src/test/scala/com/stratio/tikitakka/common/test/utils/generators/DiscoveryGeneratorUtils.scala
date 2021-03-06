/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.common.test.utils.generators

import com.stratio.tikitakka.common.model.discovery.DiscoveryAppInfo
import com.stratio.tikitakka.common.model.discovery.ServiceInfo
import org.scalacheck.Gen

object DiscoveryGeneratorUtils extends GeneratorUtils {

  def genServiceInfo: Gen[ServiceInfo] = for {
    id <- genID
    name <- genName
    address <- genIP
    port <-genPort
    tags <- genTags
  } yield ServiceInfo(id, name, address, port, tags)

  def genDiscoveryAppInfo: Gen[DiscoveryAppInfo] = for {
    id <- genID
    name <- genName
    size <- Gen.choose(1, 4)
    services <- Gen.listOfN(size, genServiceInfo)
    tags = services.flatMap(_.tags)
  } yield DiscoveryAppInfo(id, name, services, tags)

}
