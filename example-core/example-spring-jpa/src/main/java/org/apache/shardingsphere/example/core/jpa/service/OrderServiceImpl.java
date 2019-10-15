/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.example.core.jpa.service;

import org.apache.shardingsphere.example.core.api.repository.AddressRepository;
import org.apache.shardingsphere.example.core.api.repository.OrderItemRepository;
import org.apache.shardingsphere.example.core.api.repository.OrderRepository;
import org.apache.shardingsphere.example.core.api.service.ExampleService;
import org.apache.shardingsphere.example.core.jpa.entity.AddressEntity;
import org.apache.shardingsphere.example.core.jpa.entity.OrderEntity;
import org.apache.shardingsphere.example.core.jpa.entity.OrderItemEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class OrderServiceImpl implements ExampleService {

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private OrderItemRepository orderItemRepository;

	@Resource
	private AddressRepository addressRepository;

	@Override
	public void initEnvironment() throws SQLException {
		// for (int i = 1; i <= 10; i++) {
		// AddressEntity entity = new AddressEntity();
		// entity.setAddressId((long) i);
		// entity.setAddressName("address_" + String.valueOf(i));
		// addressRepository.insert(entity);
		// }
	}

	public List<Long> insertAddr() throws SQLException {
		List<Long> result = new ArrayList<>(10);
		for (int i = 1; i <= 10; i++) {
			AddressEntity entity = new AddressEntity();
			entity.setAddressId((long) i);
			entity.setAddressName("address_" + String.valueOf(i));
			try {
				addressRepository.insert(entity);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			result.add((long) entity.getAddressId());
		}
		return result;
	}

	private void deleteAddrData(final List<Long> addrs) {
		System.out.println("---------------------------- Delete addr ----------------------------");
		for (Long each : addrs) {
			try {
				addressRepository.delete(each);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void cleanEnvironment() {
	}

	@Override
	// @Transactional
	public void processSuccess() throws SQLException {
		System.out.println("-------------- Process Success Begin ---------------");
		// List<Long> orderIds = insertData();
		// printData();
		// deleteData(orderIds);
		// printData();

		List<Long> addrs = insertAddr();

		//deleteAddrData(addrs);
		System.out.println("-------------- Process Success Finish --------------");
	}

	@Override
	@Transactional
	public void processFailure() throws SQLException {
		System.out.println("-------------- Process Failure Begin ---------------");
		insertData();
		System.out.println("-------------- Process Failure Finish --------------");
		throw new RuntimeException("Exception occur for transaction test.");
	}

	private List<Long> insertData() {
		System.out.println("---------------------------- Insert Data ----------------------------");
		List<Long> result = new ArrayList<>(10);
		for (int i = 1; i <= 10; i++) {
			try {
				OrderEntity order = new OrderEntity();
				order.setUserId(i);
				order.setAddressId(i);
				order.setStatus("INSERT_TEST_JPA");
				orderRepository.insert(order);
				OrderItemEntity item = new OrderItemEntity();
				item.setOrderId(order.getOrderId());
				item.setUserId(i);
				item.setStatus("INSERT_TEST_JPA");
				orderItemRepository.insert(item);
				result.add(order.getOrderId());
			} catch (Exception ex) {
				System.out.println("---------------------------- Insert Data Exception ----------------------------");
			}
		}
		return result;
	}

	private void deleteData(final List<Long> orderIds) throws SQLException {
		System.out.println("---------------------------- Delete Data ----------------------------");
		for (Long each : orderIds) {
			orderRepository.delete(each);
			orderItemRepository.delete(each);
		}
	}

	@Override
	public void printData() throws SQLException {
		System.out.println("---------------------------- Print Order Data -----------------------");
		for (Object each : orderRepository.selectAll()) {
			System.out.println(each);
		}
		System.out.println("---------------------------- Print OrderItem Data -------------------");
		for (Object each : orderItemRepository.selectAll()) {
			System.out.println(each);
		}
	}
}
