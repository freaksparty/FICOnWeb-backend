/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
@Table(name="UseCase")
public class UseCase {
	
	private int useCaseId;
	private String useCaseName;
	
	public UseCase() {};
	
	public UseCase(String name)
	{
		super();
		this.useCaseName = name;
	}
	
	@Column(name = "useCase_id")
	@SequenceGenerator(name = "useCaseIdGenerator", sequenceName = "useCaseSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "useCaseIdGenerator")
	public int getUserCaseId() {
		return this.useCaseId;
	}
	
	public void setUserCaseId(int newId)
	{
		this.useCaseId = newId;
	}
	
	@Column(name = "useCase_name")
	public String getUseCaseName()
	{
		return this.useCaseName;
	}
	
	public void setUseCaseName(String newName) {
		this.useCaseName = newName;
	}
}
