/**
 * Copyright 2010-2011 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.ralscha.extdirectspring.util;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertSame;

import java.security.Principal;
import java.util.Locale;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * Tests for {@link SupportedParameterTypes}.
 * 
 * @author Ralph Schaer
 */
public class SupportedParametersTest {

	@Test
	public void testIsSupported() {
		assertThat(SupportedParameterTypes.values().length).isEqualTo(5);
		assertThat(SupportedParameterTypes.isSupported(String.class)).isFalse();
		assertThat(SupportedParameterTypes.isSupported(null)).isFalse();
		assertThat(SupportedParameterTypes.isSupported(MockHttpServletResponse.class)).isTrue();
		assertThat(SupportedParameterTypes.isSupported(MockHttpServletRequest.class)).isTrue();
		assertThat(SupportedParameterTypes.isSupported(MockHttpSession.class)).isTrue();
		assertThat(SupportedParameterTypes.isSupported(Locale.class)).isTrue();
		assertThat(SupportedParameterTypes.isSupported(Principal.class)).isTrue();
	}

	@Test
	public void testResolveParameter() {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/action/api-debug.js");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Locale en = Locale.ENGLISH;

		assertThat(SupportedParameterTypes.resolveParameter(String.class, request, response, en)).isNull();
		assertSame(request,
				SupportedParameterTypes.resolveParameter(MockHttpServletRequest.class, request, response, en));
		assertSame(request.getSession(),
				SupportedParameterTypes.resolveParameter(MockHttpSession.class, request, response, en));
		assertSame(request.getUserPrincipal(),
				SupportedParameterTypes.resolveParameter(Principal.class, request, response, en));
		assertSame(response,
				SupportedParameterTypes.resolveParameter(MockHttpServletResponse.class, request, response, en));
		assertSame(en, SupportedParameterTypes.resolveParameter(Locale.class, request, response, en));
	}

}
