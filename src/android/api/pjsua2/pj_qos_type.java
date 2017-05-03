/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public final class pj_qos_type {
  public final static pj_qos_type PJ_QOS_TYPE_BEST_EFFORT = new pj_qos_type("PJ_QOS_TYPE_BEST_EFFORT");
  public final static pj_qos_type PJ_QOS_TYPE_BACKGROUND = new pj_qos_type("PJ_QOS_TYPE_BACKGROUND");
  public final static pj_qos_type PJ_QOS_TYPE_VIDEO = new pj_qos_type("PJ_QOS_TYPE_VIDEO");
  public final static pj_qos_type PJ_QOS_TYPE_VOICE = new pj_qos_type("PJ_QOS_TYPE_VOICE");
  public final static pj_qos_type PJ_QOS_TYPE_CONTROL = new pj_qos_type("PJ_QOS_TYPE_CONTROL");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static pj_qos_type swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + pj_qos_type.class + " with value " + swigValue);
  }

  private pj_qos_type(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private pj_qos_type(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private pj_qos_type(String swigName, pj_qos_type swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static pj_qos_type[] swigValues = { PJ_QOS_TYPE_BEST_EFFORT, PJ_QOS_TYPE_BACKGROUND, PJ_QOS_TYPE_VIDEO, PJ_QOS_TYPE_VOICE, PJ_QOS_TYPE_CONTROL };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

