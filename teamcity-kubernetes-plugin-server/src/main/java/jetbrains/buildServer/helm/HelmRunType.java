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

package jetbrains.buildServer.helm;

import java.util.*;
import jetbrains.buildServer.requirements.Requirement;
import jetbrains.buildServer.requirements.RequirementType;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.util.PropertiesUtil;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static jetbrains.buildServer.helm.HelmConstants.HELM_PATH_CONFIG_PARAM;
import static jetbrains.buildServer.helm.HelmConstants.HELM_RUN_TYPE;

/**
 * Created by Evgeniy Koshkin (evgeniy.koshkin@jetbrains.com) on 28.11.17.
 */
public class HelmRunType extends RunType {
    private final PluginDescriptor myPluginDescriptor;

    public HelmRunType(PluginDescriptor pluginDescriptor, RunTypeRegistry runTypeRegistry) {
        myPluginDescriptor = pluginDescriptor;
        runTypeRegistry.registerRunType(this);
    }

    @NotNull
    @Override
    public String getType() {
        return HELM_RUN_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Helm";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Runs a Helm command";
    }

    @Nullable
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return properties -> {
            List<InvalidProperty> result = new ArrayList<>();
            final String commandId = properties.get(HelmConstants.COMMAND_ID);
            if (PropertiesUtil.isEmptyOrNull(commandId)) {
                result.add(new InvalidProperty(HelmConstants.COMMAND_ID, "Command to run must be specified"));
            }
            final HelmCommand helmCommand = HelmCommands.find(commandId);
            if(helmCommand != null){
                PropertiesProcessor propertiesProcessor = helmCommand.getPropertiesProcessor();
                if(propertiesProcessor != null) {
                    result.addAll(propertiesProcessor.process(properties));
                }
            }
            return result;
        };
    }

    @Nullable
    @Override
    public String getEditRunnerParamsJspFilePath() {
        return myPluginDescriptor.getPluginResourcesPath("helm/editHelm.jsp");
    }

    @Nullable
    @Override
    public String getViewRunnerParamsJspFilePath() {
        return myPluginDescriptor.getPluginResourcesPath("helm/viewHelm.jsp");
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        return null;
    }

    @NotNull
    @Override
    public List<Requirement> getRunnerSpecificRequirements(@NotNull Map<String, String> runParameters) {
        return Collections.singletonList(new Requirement(HELM_PATH_CONFIG_PARAM, null, RequirementType.EXISTS));
    }

    @NotNull
    @Override
    public String describeParameters(@NotNull Map<String, String> parameters) {
        HelmCommand helmCommand = HelmCommands.find(parameters.get(HelmConstants.COMMAND_ID));
        return helmCommand == null
                ? super.describeParameters(parameters)
                : "Command: " + helmCommand.getDisplayName() + "\n" + helmCommand.describeParameters(parameters);
    }
}
