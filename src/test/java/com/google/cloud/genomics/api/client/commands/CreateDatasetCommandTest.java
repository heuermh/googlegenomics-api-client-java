/*
Copyright 2014 Google Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.google.cloud.genomics.api.client.commands;

import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.genomics.Genomics;
import com.google.api.services.genomics.model.Dataset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CreateDatasetCommandTest {
  @Mock Genomics genomics;
  @Mock Genomics.Datasets datasets;
  @Mock Genomics.Datasets.Create datasetCreate;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateDataset() throws Exception {
    CreateDatasetCommand command = new CreateDatasetCommand();
    command.setDataStoreFactory(new MemoryDataStoreFactory());

    command.projectId = 5L;
    command.name = "dataset";

    Dataset expectedDataset = new Dataset().setName("dataset").setProjectId(5L).setIsPublic(false);

    Mockito.when(genomics.datasets()).thenReturn(datasets);
    Mockito.when(datasets.create(expectedDataset)).thenReturn(datasetCreate);
    Mockito.when(datasetCreate.execute()).thenReturn(expectedDataset.clone().setId("id"));

    command.handleRequest(genomics);

    Map<String,String> previousDatasets = command.getPreviousDatasets();
    assertEquals(1, previousDatasets.size());
    assertEquals("dataset", previousDatasets.get("id"));
  }

}