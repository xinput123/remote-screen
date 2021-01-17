package com.xinput.screen.client;

public interface IMouseOperation {
  int leftClick();

  int leftDoubleClick();

  int leftDown();

  int leftUp();

  int rightClick();

  int rightDown();

  int rightUp();

  int middleClick();

  int middleDown();

  int middleUp();

  int wheel(int rotation);

  int moveTo(int x, int y);
}
