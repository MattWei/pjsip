var exec = require('cordova/exec');

function PJSIP() {
}

PJSIP.prototype.isSupported = function(success, error) {
    exec(success, error, "PJSIP", "issupported", []);
};

PJSIP.prototype.connect = function(arg0, arg1, arg2, arg3, success, error) {
    exec(success, error, "PJSIP", "connect", [arg0, arg1, arg2, arg3]);
};

PJSIP.prototype.makeCall = function(arg0, success, error) {
    arg0 = arg0.trim().replace(/\.| /g, '');
    exec(success, error, "PJSIP", "makecall", [arg0]);
};

PJSIP.prototype.endCall = function(success, error) {
    exec(success, error, "PJSIP", "endcall", []);
};

PJSIP.prototype.disconnect = function(success, error) {
    exec(success, error, "PJSIP", "disconnect", []);
};

PJSIP.prototype.isConnected = function(success, error) {
    exec(success, error, "PJSIP", "isconnected", []);
};

PJSIP.prototype.activateSpeaker = function(arg0, success, error) {
    exec(success, error, "PJSIP", "activatespeaker", [arg0]);
};

PJSIP.prototype.dtmfCall = function(arg0, success, error) {
    exec(success, error, "PJSIP", "dtmfcall", [arg0]);
};

PJSIP.prototype.muteMicrophone = function(arg0, success, error) {
    exec(success, error, "PJSIP", "mutemicrophone", [arg0]);
};

PJSIP.prototype.holdCall = function(arg0, success, error) {
    exec(success, error, "PJSIP", "holdcall", [arg0]);
};

PJSIP.prototype.declineCall = function(success, error) {
    exec(success, error, "PJSIP", "declinecall", []);
};

PJSIP.prototype.acceptCall = function(success, error) {
    exec(success, error, "PJSIP", "acceptcall", []);
};

PJSIP.prototype.callState = function(arg0, success, error) {

  switch (arg0.state){
    case "outcall":
      this.stateCallOut(arg0.outGoingCallNumber);
      break;
    case "established":
      this.stateCallEstablished();
      break;
    case "endcall":
      this.stateCallEnd();
      break;
    case "incall":
      this.stateCallIn(arg0.inComingCallNumber);
      break;
  }
};


PJSIP.prototype.regState = function(arg0, success, error) {
    /*
      switch (arg0.code){
        case "outcall":
          this.stateCallOut(arg0.outGoingCallNumber);
          break;
        case "established":
          this.stateCallEstablished();
          break;
        case "endcall":
          this.stateCallEnd();
          break;
        case "incall":
          this.stateCallIn(arg0.inComingCallNumber);
          break;
      }
      */
    //console.log("###########PJSIP.prototype.regState" + arg0);
    this.accountRegStatus(arg0.code, arg0.reason, arg0.expiration);
};

PJSIP.prototype.actions = function(arg0,success, error) {

  switch (arg0.action){
    case "requestpermission":
      this.requestPermission(arg0.success);
      break;
  }
};

PJSIP.prototype.stateCallOut = function(arg0){}
PJSIP.prototype.stateCallEstablished = function(){}
PJSIP.prototype.stateCallEnd = function(){}
PJSIP.prototype.stateCallIn = function(arg0){}
PJSIP.prototype.requestPermission = function(arg0){}
PJSIP.prototype.accountRegStatus = function(code, reason, expiration) {}

module.exports = new PJSIP();
