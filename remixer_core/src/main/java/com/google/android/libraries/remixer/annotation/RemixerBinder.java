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

package com.google.android.libraries.remixer.annotation;

/**
 * Used to bind activities to their implicitly generated remixes.
 */
public class RemixerBinder {

  /**
   * Binds an activity's remixer to its generated remixes.
   * @throws RemixerBindingException When there is an issue instantiating the Binder class
   */
  public static <T> void bind(T activity) {
    try {
      Class<?> bindingClass =
          Class.forName(activity.getClass().getCanonicalName() + "_RemixerBinder");

      @SuppressWarnings("unchecked")
      Binder<T> binder = (Binder<T>) bindingClass.newInstance();
      binder.bindInstance(activity);
    } catch (ClassNotFoundException ex) {
      throw new RemixerBindingException(
          "Remixer binder class can not be found or initialized for "
              + activity.getClass().toString(),
          ex);
    } catch (InstantiationException ex) {
      throw new RemixerBindingException(
          "Remixer binder class can not instantiated for "
              + activity.getClass().toString(),
          ex);
    } catch (IllegalAccessException ex) {
      throw new RemixerBindingException(
          "Remixer binder class can not be instantiated for "
              + activity.getClass().toString(),
          ex);
    }
  }

  public abstract static class Binder<T> {

    /**
     * Bind an activity's remixer instance to its generated remixes.
     */
    public abstract void bindInstance(T activity);
  }
}
