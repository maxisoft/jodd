// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.injector;

import jodd.madvoc.ActionRequest;
import jodd.madvoc.ScopeData;
import jodd.madvoc.ScopeType;
import jodd.madvoc.component.ScopeDataResolver;

/**
 * Abstract base class for HTTP scopes injection.
 */
public abstract class BaseScopeInjector {

	protected final ScopeDataResolver scopeDataResolver;
	protected final ScopeType scopeType;

	/**
	 * Creates scope injector for provided {@link jodd.madvoc.ScopeType}.
	 */
	protected BaseScopeInjector(ScopeType scopeType, ScopeDataResolver scopeDataResolver) {
		this.scopeType = scopeType;
		this.scopeDataResolver = scopeDataResolver;
	}

	// ---------------------------------------------------------------- flags

	protected boolean silent = false;

	/**
	 * Returns <code>true</code> if injection is silent.
	 */
	public boolean isSilent() {
		return silent;
	}

	/**
	 * Defines if injection should throw exceptions or to be silent.
	 */
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	// ---------------------------------------------------------------- target

	/**
	 * Sets target bean property, optionally creates instance if doesn't exist.
	 */
	protected void setTargetProperty(Target target, String name, Object value) {
		target.writeValue(name, value, silent);
	}

	/**
	 * Reads target property.
	 */
	protected Object getTargetProperty(Target target, ScopeData.Out out) {
		if (out.target == null) {
			return target.readValue(out.name);
		} else {
			return target.readValue(out.target);
		}
	}

	// ---------------------------------------------------------------- matched property

	/**
	 * Returns matched property name or <code>null</code> if name is not matched.
	 * <p>
	 * Matches if attribute name matches the required field name. If the match is positive,
	 * injection or outjection is performed on the field.
	 * <p>
	 * Parameter name matches field name if param name starts with field name and has
	 * either '.' or '[' after the field name.
	 * <p>
	 * Returns real property name, once when name is matched.
	 */
	protected String getMatchedPropertyName(ScopeData.In in, String attrName) {
		// match
		if (attrName.startsWith(in.name) == false) {
			return null;
		}
		int requiredLen = in.name.length();
		if (attrName.length() >= requiredLen + 1) {
			char c = attrName.charAt(requiredLen);
			if ((c != '.') && (c != '[')) {
				return null;
			}
		}

		// get param
		if (in.target == null) {
			return attrName;
		}
		return in.target + attrName.substring(in.name.length());
	}


	// ---------------------------------------------------------------- delegates

	/**
	 * Returns scope data from action request and for current scope type.
	 */
	public ScopeData[] lookupScopeData(ActionRequest actionRequest) {
		return actionRequest.getActionConfig().scopeData[scopeType.value()];
	}

	/**
	 * Returns IN data for current scope type.
	 */
	public ScopeData.In[] lookupInData(ScopeData[] scopeData) {
		if (scopeData == null) {
			return null;
		}
		ScopeData sd = scopeData[scopeType.value()];
		if (sd == null) {
			return null;
		}

		return sd.in;
	}

}