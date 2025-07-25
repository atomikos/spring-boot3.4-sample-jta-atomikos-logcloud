/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.atomikos;



import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.system.CapturedOutput;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;


/**
 * Basic integration tests for demo application.
 *
 * @author Phillip Webb
 */

@ExtendWith(OutputCaptureExtension.class)
public class SampleAtomikosApplicationTests {


	@Test
	public void testTransactionRollback(CapturedOutput capturedOutput) throws Exception {
		SampleAtomikosApplication.main(new String[] {});
		String output = capturedOutput.getOut();
		long count = output.split("----> josh").length - 1;
		assertThat(count).isGreaterThanOrEqualTo(1);
		assertThat(output).has(substring(1, "Simulated error"));
	}

	private Condition<String> substring(int times, String substring) {
		return new Condition<String>("containing '" + substring + "' " + times + " times") {

			@Override
			public boolean matches(String value) {
				int i = 0;
				while (value.contains(substring)) {
					int beginIndex = value.indexOf(substring) + substring.length();
					value = value.substring(beginIndex);
					i++;
				}
				return i == times;
			}

		};
	}

}
