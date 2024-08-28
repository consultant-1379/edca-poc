{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "eric-edca-drg-simulator.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create chart version as used by the chart label.
*/}}
{{- define "eric-edca-drg-simulator.version" -}}
{{- printf "%s" .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "eric-edca-drg-simulator.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- $name | trunc 63 | trimSuffix "-" -}}
{{/* Ericsson mandates the name defined in metadata should start with chart name. */}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "eric-edca-drg-simulator.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create image registry url
*/}}
{{- define "eric-edca-drg-simulator.registryUrl" -}}
{{- if .Values.imageCredentials.registry -}}
{{- if .Values.imageCredentials.registry.url -}}
{{- print .Values.imageCredentials.registry.url -}}
{{- else if .Values.global.registry.url -}}
{{- print .Values.global.registry.url -}}
{{- else -}}
""
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create image pull secrets
*/}}
{{- define "eric-edca-drg-simulator.pullSecrets" -}}
{{- if .Values.imageCredentials.pullSecret -}}
{{- print .Values.imageCredentials.pullSecret -}}
{{- else if .Values.global.pullSecret -}}
{{- print .Values.global.pullSecret -}}
{{- else -}}
""
{{- end -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "eric-edca-drg-simulator.labels" -}}
app.kubernetes.io/name: {{ include "eric-edca-drg-simulator.name" . }}
helm.sh/chart: {{ include "eric-edca-drg-simulator.chart" . }}
app.kubernetes.io/version: {{ include "eric-edca-drg-simulator.version" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{/*
Create the name for the tls secret.
*/}}
{{- define "eric-edca-drg-simulator.ingressTLSSecret" -}}
{{- if .Values.ingress.tls.existingSecret -}}
  {{- .Values.ingress.tls.existingSecret -}}
{{- else -}}
  {{- template "eric-edca-drg-simulator.name" . -}}-ingress-external-tls-secret
{{- end -}}
{{- end -}}

{{- define "eric-edca-drg-simulator.product-info" -}}
ericsson.com/product-name: "EDCA DRG Simulator"

{{- end -}}