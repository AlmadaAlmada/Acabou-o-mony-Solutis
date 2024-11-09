package com.acabouomony.solutis.performanceandscalability.service;


import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.AppsAPIGroupDSL;
import org.springframework.stereotype.Service;


@Service
public class ScalingService {

    private final CacheManagerService cacheManagerService;

    public ScalingService(CacheManagerService cacheManagerService) {
        this.cacheManagerService = cacheManagerService;
    }

    public void adjustScalingAndPerformance(String message) {
        if (message.contains("High CPU")) {
            System.out.println("High CPU usage detected. Scaling up replicas...");
            scaleUpReplicas();
        } else if (message.contains("High Memory")) {
            System.out.println("High Memory usage detected. Adjusting cache settings...");
            cacheManagerService.adjustCacheSettings();
        } else {
            System.out.println("Tipo de evento não reconhecido. Nenhuma ação realizada.");
        }
    }

    public void scaleUpReplicas() {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            AppsAPIGroupDSL apps = client.apps();
            var deploymentResource = apps.deployments().inNamespace("default").withName("NOME_DO_DEPLOYMENT");
            Deployment deployment = deploymentResource.get();

            if (deployment != null) {
                int currentReplicas = deployment.getSpec().getReplicas();
                int newReplicaCount = currentReplicas + 1;

                deployment.getSpec().setReplicas(newReplicaCount);
                deploymentResource.patch(deployment);

                System.out.println("Scaled up replicas to: " + newReplicaCount);
            } else {
                System.out.println("Deployment não encontrado no namespace especificado.");
            }
        } catch (KubernetesClientException e) {
            System.err.println("Erro ao tentar escalar o deployment: " + e.getMessage());
        }
    }
}