package com.xinput.screen.client;

public interface IKeyOperation {
  void keyDown(int keycode);

  void keyUp(int keycode);

  void keyType(int keycode);
}
