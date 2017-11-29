/*******************************************************************************
 * QMetry Automation Framework provides a powerful and versatile platform to
 * author
 * Automated Test Cases in Behavior Driven, Keyword Driven or Code Driven
 * approach
 * Copyright 2016 Infostretch Corporation
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 * You should have received a copy of the GNU General Public License along with
 * this program in the name of LICENSE.txt in the root folder of the
 * distribution. If not, see https://opensource.org/licenses/gpl-3.0.html
 * See the NOTICE.TXT file in root folder of this source files distribution
 * for additional information regarding copyright ownership and licenses
 * of other open source software / files used by QMetry Automation Framework.
 * For any inquiry or need additional information, please contact
 * support-qaf@infostretch.com
 *******************************************************************************/

package com.qmetry.qaf.automation.step;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getStepMapping;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qmetry.qaf.automation.step.client.text.BDDDefinitionHelper;
import com.qmetry.qaf.automation.step.client.text.BDDDefinitionHelper.BDDKeyword;
import com.qmetry.qaf.automation.step.client.text.BDDDefinitionHelper.ParamType;
import com.qmetry.qaf.automation.util.StringUtil;

/**
 * A proxy step class facilitate to create non java custom steps. It is also a
 * step executor for any java or custom step.
 * com.qmetry.qaf.automation.step.StringTestStep.java
 * 
 * @author chirag.jayswal
 */
public class StringTestStep extends BaseTestStep {

	private String resultParameterName;
	private Map<String, Object> context;
	private String codeSnippet;
	private TestStep step;

	public StringTestStep(String name, Object... actualArgs) {
		this.name = name;
		description = name;
		setActualArgs(actualArgs);
		context = new HashMap<String, Object>();
	}

	public StringTestStep(String name, Map<String, Object> context,
			Object... actualArgs) {
		this(name, actualArgs);
		this.context = context;
	}


	public void initStep() {
		if (step == null) {
			step = getTestStep();
			if (null != step) {
				step.setActualArgs(actualArgs);
				step.setDescription(description);
				step.getStepExecutionTracker()
						.setContext(getStepExecutionTracker().getContext());
			}
		}
	}

	public void initStep(Map<String, Object> context) {
		if (step == null) {
			step = getTestStep();
			if (null != step) {
				step.setActualArgs(actualArgs);
				step.setDescription(description);
				step.getStepExecutionTracker().setContext(context);
			}
		}
	}

	public TestStep deepClone() {
		initStep();
		TestStep s = step.clone();
		s.setActualArgs(actualArgs);
		s.getStepExecutionTracker().setContext(getStepExecutionTracker().getContext());
		return s.clone();
	}

	@Override
	public Object[] getActualArgs() {
		return step == null ? super.getActualArgs() : step.getActualArgs();
	}

	@Override
	public void setActualArgs(Object... args) {
		super.setActualArgs(args);
		if (step != null) {
			step.setActualArgs(args);
		}
	}

	public StringTestStep() {
	}

	public static void addSteps(Map<String, TestStep> steps) {
		getStepMapping().putAll(steps);
	}

	public static void addStep(String name, TestStep step) {
		getStepMapping().put(name.toUpperCase(), step);
	}

	public void setResultParamName(String resultParamName) {
		resultParameterName = resultParamName;
	}

	@Override
	public Object execute() {
		if (DryRunAnalyzer.isDryRun(this)) {
			return null;
		}
		initStep();

		if (null != step) {
			Object retVal = null;
			try {
				retVal = step.execute();
				if (isNotBlank(resultParameterName)) {
					if (resultParameterName.indexOf("${") == 0) {
						getBundle().setProperty(resultParameterName, retVal);

					} else {
						getBundle().setProperty("${" + resultParameterName + "}", retVal);

					}

				}
			} catch (Error ae) {
				StepInvocationException se = new StepInvocationException(this, ae);
				ae.setStackTrace(se.getStackTrace());
				throw ae;
			} catch (Throwable e) {
				StepInvocationException se = new StepInvocationException(this, e);
				RuntimeException re =
						(RuntimeException.class.isAssignableFrom(e.getClass())
								? (RuntimeException) e : new RuntimeException(e));
				re.setStackTrace(se.getStackTrace());
				throw re;
			}
			return retVal;
		}

		throw new StepNotFoundException(this);
	}

	@Override
	public String getSignature() {
		return name + "@" + getFileName() + "#" + getLineNumber();
	}

	public static Object execute(String stepName, Object... args) {
		StringTestStep proxy = new StringTestStep(stepName, args);
		proxy.initStep();
		return proxy.execute();
	}

	@Override
	public TestStep clone() {
		StringTestStep clone = new StringTestStep(name,
				(null != actualArgs ? actualArgs.clone() : null));
		clone.setFileName(fileName);
		clone.setLineNumber(lineNumber);
		clone.resultParameterName = resultParameterName;
		clone.context = context;
		return clone;
	}

	public TestStep getTestStep() {
		TestStep step = null;
		String prefix = BDDKeyword.getKeywordFrom(name);
		String nameWithoutPrefix = removePrefix(prefix, name);
		if (getStepMapping().containsKey(nameWithoutPrefix.toUpperCase())) {

			step = getStepMapping().get(nameWithoutPrefix.toUpperCase()).clone();
		} else if (getBundle().getBoolean("step.natural.lang.support", true)) {
			Collection<TestStep> set = getStepMapping().values();
			for (TestStep stepName : set) {
				BDDStepMatcher matcher = BDDStepMatcherFactory.getStepMatcher(stepName);
				if (matcher.matches(stepName.getDescription(), nameWithoutPrefix,
						context)) {
					List<String[]> parameters = matcher.getArgsFromCall(
							stepName.getDescription(), nameWithoutPrefix, context);
					Object[] params = new Object[parameters.size()];
					for (int i = 0; i < parameters.size(); i++) {
						params[i] = parameters.get(i)[0];
					}
					setActualArgs(params);
					step = getStepMapping().get(stepName.getName().toUpperCase()).clone();
					step.getStepExecutionTracker().setType(prefix);
					break;
				}
			}
		}

		return step;
	}

	private static String removePrefix(String prefix, String s) {
		if (StringUtil.isBlank(prefix))
			return s;
		return s.substring(prefix.length()).trim();
	}

	@Override
	protected Object doExecute() {
		// this is proxy!...
		return null;
	}

	@Override
	public StepExecutionTracker getStepExecutionTracker() {
		return step != null ? step.getStepExecutionTracker() : null;
	}

	public String getCodeSnippet() {
		if (StringUtil.isBlank(codeSnippet)) {
			generateCodeSnippet();
		}
		return codeSnippet;
	}

	private void generateCodeSnippet() {
		absractArgsAandSetDesciption();

		StringBuilder snippet = new StringBuilder(
				"\n/**\n* Auto-generated code snippet by QMetry Automation Framework.\n*/");
		snippet.append("\n@QAFTestStep(description=\"" + description + "\")");

		snippet.append("\npublic void " + name + "(");

		for (int i = 0; i < actualArgs.length; i++) {
			String arg = ((String) actualArgs[i]).trim();
			if (arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false")) {
				snippet.append("Boolean b" + i);
			} else {
				ParamType type = BDDDefinitionHelper.ParamType.getType(arg);
				snippet.append(type.getArgString() + i);
			}
			if (i < actualArgs.length - 1)
				snippet.append(',');
		}
		snippet.append("){");
		snippet.append(
				"\n\t//TODO: remove NotYetImplementedException and call test steps");
		snippet.append("\n\tthrow new NotYetImplementedException();\n}");

		codeSnippet = snippet.toString();
	}
	private void absractArgsAandSetDesciption() {
		String prefix = BDDKeyword.getKeywordFrom(name);
		if (actualArgs == null || actualArgs.length == 0) {
			final String REGEX = ParamType.getParamValueRegx();
			List<String> arguments = new ArrayList<String>();

			description = removePrefix(prefix, name);

			Pattern p = Pattern.compile(REGEX);
			Matcher m = p.matcher(description); // get a matcher object
			int count = 0;

			while (m.find()) {
				String arg = description.substring(m.start(), m.end());
				arguments.add(arg);
				count++;
			}
			for (int i = 0; i < count; i++) {
				description = description.replaceFirst(Pattern.quote(arguments.get(i)),
						Matcher.quoteReplacement("{" + i + "}"));
			}
			actualArgs = arguments.toArray(new String[]{});
		}
		name = StringUtil
				.toCamelCaseIdentifier(description.length() > 0 ? description : name);
	}

}
