/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.columbus.test.utils.consul

import com.stratio.tikitakka.common.test.utils.generators.GeneratorUtils
import org.scalacheck.Gen

object ConsulGeneratorUtils extends GeneratorUtils {

  def genAgentService: Gen[AgentService] = for {
    id <- genID
    service = id
    tags <- genTags
    address <- genIP
    port <- Gen.choose(80, 50000)
  } yield AgentService(id, service, tags, address, port)
}
