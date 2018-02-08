cordova.define("gr.navarino.PJSIP.PJSIP", function(require, exports, module) {
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

PJSIP.prototype.sendInstantMessage = function(arg0, arg1, success, error) {
    exec(success, error, "PJSIP", "sendInstantMessage", [arg0, arg1]);
};

PJSIP.prototype.addBuddy = function(arg0, success, error) {
    exec(success, error, "PJSIP", "addBuddy", [arg0]);
};

PJSIP.prototype.deleteBuddy = function(arg0, success, error) {
    exec(success, error, "PJSIP", "deleteBuddy", [arg0]);
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

  switch (arg0.action) {
    case "requestpermission":
      this.requestPermission(arg0.success);
      break;
  }
};


PJSIP.prototype.buddyState = function(arg0, success, error) {
    this.onBuddyState(arg0);
};
PJSIP.prototype.instantMessage = function(arg0, success, error) {
    this.onInstantMessage(arg0);
};

PJSIP.prototype.stateCallOut = function(arg0){}
PJSIP.prototype.stateCallEstablished = function(){}
PJSIP.prototype.stateCallEnd = function(){}
PJSIP.prototype.stateCallIn = function(arg0){}
PJSIP.prototype.requestPermission = function(arg0){}
PJSIP.prototype.accountRegStatus = function(code, reason, expiration) {}

PJSIP.prototype.onBuddyState = function(arg0) {}
PJSIP.prototype.onInstantMessage = function(arg0) {}


///////////////Player//////////////////
PJSIP.prototype.makeFilesCall = function(arg0, arg1, arg2, success, error) {
    exec(success, error, "PJSIP", "makeFilesCall", [arg0, arg1, arg2]);
};

PJSIP.prototype.changePlayingSong = function(arg0, success, error) {
    exec(success, error, "PJSIP", "changePlayingSong", [arg0]);
}

PJSIP.prototype.changeFilesCallRepeatType = function(arg0, success, error) {
    exec(success, error, "PJSIP", "changeFilesCallRepeatType", [arg0]);
};

PJSIP.prototype.addMusicesToPlaylistCall = function(arg0, success, error) {
    exec(success, error, "PJSIP", "addMusicesToPlaylistCall", [arg0]);
};

PJSIP.prototype.deleteMusicFromPlaylistCall= function(arg0, success, error) {
    exec(success, error, "PJSIP", "deleteMusicFromPlaylistCall", [arg0]);
}

PJSIP.prototype.reorderMusic= function(arg0, arg1, success, error) {
    exec(success, error, "PJSIP", "reorderMusic", [arg0, arg1]);
}



PJSIP.prototype.playStatus = function(arg0, success, error) {
    switch (arg0.type){
      case "Start":
        this.playStart(arg0.index, arg0.param);
        break;
      case "Playing":
        this.playOn(arg0.index, arg0.param);
        break;
      case "Finish":
        this.playFinish(arg0.index);
        break;
    }
};

PJSIP.prototype.playStart = function(arg0, arg1) {}
PJSIP.prototype.playOn = function(arg0, arg1) {}
PJSIP.prototype.playFinish = function(arg0) {}

module.exports = new PJSIP();

});
