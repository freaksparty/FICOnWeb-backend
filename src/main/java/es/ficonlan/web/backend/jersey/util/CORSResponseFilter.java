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

package es.ficonlan.web.backend.jersey.util;

 
import java.io.IOException;
 
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class CORSResponseFilter implements ContainerResponseFilter {
 
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
 
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
 
        headers.add("Access-Control-Allow-Origin", "*");
        //headers.add("Access-Control-Allow-Origin", "http://ficonlan.es");        
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");    
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, sessionId");
        //headers.add("Access-Control-Allow-Headers", "sessionId");
        
    }
 
}