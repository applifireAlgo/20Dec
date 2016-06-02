package com.app.bean;
  public  class PasswordLogic {

    public char chars;

    public int numChars;

    public PasswordLogic(char chars, int numChars) {

      this.numChars = numChars;

      this.chars = chars;

    }

    public char getChars() {

      return chars;

    }

    public void setChars(char chars) {

      this.chars = chars;

    }

    public int getNumChars() {

      return numChars;

    }

    public void setNumChars(int numChars) {

      this.numChars = numChars;

    }

  }

