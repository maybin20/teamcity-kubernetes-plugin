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

package jetbrains.buildServer.clouds.kubernetes;

import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import jetbrains.buildServer.clouds.CloudErrorInfo;
import jetbrains.buildServer.clouds.CloudImage;
import jetbrains.buildServer.clouds.kubernetes.connector.ImagePullPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by ekoshkin (koshkinev@gmail.com) on 28.05.17.
 */
public interface KubeCloudImage extends CloudImage {
    @Nullable
    String getDockerImage();

    @Nullable
    ImagePullPolicy getImagePullPolicy();

    @Nullable
    String getDockerArguments();

    @Nullable
    String getDockerCommand();

    void populateInstances();

    void addStartedInstance(final KubeCloudInstance instance);

    @NotNull
    String getPodSpecMode();

    @Nullable
    String getPodTemplate();

    @Nullable
    String getPVCTemplate();

    @Nullable
    String getSourceDeploymentName();

    int getRunningInstanceCount();

    int getInstanceLimit();

    @NotNull
    String getAgentName(@NotNull String instanceName);

    @NotNull
    String getAgentNamePrefix();

    void setErrorInfo(CloudErrorInfo errorInfo);
}
