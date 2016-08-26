/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.libraries.remixer.annotation.processor;

/**
 * Utility class that checks that a key is valid.
 */
public class KeyChecker {

  /**
   * Checks whether the given key is a valid Java Identifier Name.
   */
  public static boolean isValidKey(String key) {
    if (key.length() == 0) {
      return false;
    }
    if (!Character.isJavaIdentifierStart(key.charAt(0))) {
      return false;
    }
    for (int i = 0; i < key.length(); i++) {
      if (!Character.isJavaIdentifierStart(key.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
