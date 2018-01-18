/*
 * Copyright (C) 2015-2018, IBM Corporation
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.crail.storage.nvmf.client;

import com.ibm.disni.nvmef.spdk.NvmeStatusCodeType;

import java.io.IOException;

import org.apache.crail.CrailBuffer;

/**
 * Created by jpf on 23.05.17.
 */
public class NvmfStorageUnalignedWriteFuture extends NvmfStorageFuture {

	private CrailBuffer stagingBuffer;

	public NvmfStorageUnalignedWriteFuture(NvmfStorageEndpoint endpoint, int len, CrailBuffer stagingBuffer) {
		super(endpoint, len);
		this.stagingBuffer = stagingBuffer;
	}

	@Override
	void signal(NvmeStatusCodeType statusCodeType, int statusCode) {
		try {
			endpoint.putBuffer(stagingBuffer);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		super.signal(statusCodeType, statusCode);
	}
}
