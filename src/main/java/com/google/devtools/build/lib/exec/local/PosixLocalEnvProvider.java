// Copyright 2018 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.devtools.build.lib.exec.local;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.devtools.build.lib.vfs.Path;
import java.io.IOException;
import java.util.Map;

/** {@link LocalEnvProvider} implementation for actions running on Unix-like platforms. */
public final class PosixLocalEnvProvider implements LocalEnvProvider {

  public static final PosixLocalEnvProvider INSTANCE = new PosixLocalEnvProvider();

  /**
   * Compute an environment map for local actions on Unix-like platforms (e.g. Linux, macOS).
   *
   * <p>Returns a map with the same keys and values as {@code env}. Overrides the value of TMPDIR
   * (or adds it if not present in {@code env}) by {@code tmpDir}.
   */
  @Override
  public Map<String, String> rewriteLocalEnv(
      Map<String, String> env, Path execRoot, Path tmpDir, String productName) throws IOException {
    ImmutableMap.Builder<String, String> result = ImmutableMap.builder();
    result.putAll(Maps.filterKeys(env, k -> !k.equals("TMPDIR")));
    result.put("TMPDIR", tmpDir.getPathString());
    return result.build();
  }
}
