package com.bhachtgatsupplier.interfaces;


public interface OtpReceivedInterface {
  void onOtpReceived(String otp);
  void onOtpTimeout();
}
