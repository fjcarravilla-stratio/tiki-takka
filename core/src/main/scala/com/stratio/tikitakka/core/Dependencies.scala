/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.core

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.stratio.tikitakka.columbus.DiscoveryComponent

trait Dependencies {

  //Actor System
  val actorSystem: ActorSystem
  val materializer: ActorMaterializer

  //Actors
  val discoveryActorRef: ActorRef
  val serviceActorRef: ActorRef
  val servicesActorRef: ActorRef
  val orchestratorActorRef: ActorRef
  val upAndDownActorRef: ActorRef

  val discoveryComponent: DiscoveryComponent

}
