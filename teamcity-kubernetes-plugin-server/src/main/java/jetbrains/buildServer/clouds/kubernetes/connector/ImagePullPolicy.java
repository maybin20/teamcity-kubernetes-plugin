/*
 * Copyright 2000-2020 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jetbrains.buildServer.clouds.kubernetes.connector;

/**
 * Created by ekoshkin (koshkinev@gmail.com) on 09.06.17.
 */
public enum ImagePullPolicy {
    IfNotPresent {
        @Override
        public String getName() {
            return "IfNotPresent";
        }

        @Override
        public String getDisplayName() {
            return "If not present";
        }
    },
    Always {
        @Override
        public String getName() {
            return "Always";
        }

        @Override
        public String getDisplayName() {
            return "Always";
        }
    },
    Never {
        @Override
        public String getName() {
            return "Never";
        }

        @Override
        public String getDisplayName() {
            return "Never";
        }
    };


    public abstract String getName();
    public abstract String getDisplayName();
}
