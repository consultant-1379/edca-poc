{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "eric-edca-catalog-service.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create chart version as used by the chart label.
*/}}
{{- define "eric-edca-catalog-service.version" -}}
{{- printf "%s" .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "eric-edca-catalog-service.fullname" -}}
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
{{- define "eric-edca-catalog-service.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create image registry url
*/}}
{{- define "eric-edca-catalog-service.registryUrl" -}}
{{- if .Values.imageCredentials.registry -}}
{{- if .Values.imageCredentials.registry.url -}}
{{- print .Values.imageCredentials.registry.url -}}
{{- else if .Values.global.registry.url -}}
{{- print .Values.global.registry.url -}}
{{- else -}}
""
{{- end -}}
{{- else if .Values.global.registry.url -}}
{{- print .Values.global.registry.url -}}
{{- else -}}
""
{{- end -}}
{{- end -}}

{{/*
Create image pull secrets
*/}}
{{- define "eric-edca-catalog-service.pullSecrets" -}}
{{- if .Values.imageCredentials.registry -}}
{{- if .Values.imageCredentials.registry.pullSecret -}}
{{- print .Values.imageCredentials.registry.pullSecret -}}
{{- else if .Values.global.registry.pullSecret -}}
{{- print .Values.global.registry.pullSecret -}}
{{- else -}}
""
{{- end -}}
{{- else if .Values.global.registry.pullSecret -}}
{{- print .Values.global.registry.pullSecret -}}
{{- else -}}
""
{{- end -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "eric-edca-catalog-service.labels" -}}
app.kubernetes.io/name: {{ include "eric-edca-catalog-service.name" . }}
helm.sh/chart: {{ include "eric-edca-catalog-service.chart" . }}
app.kubernetes.io/version: {{ include "eric-edca-catalog-service.version" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}



{{- define "eric-edca-catalog-service.product-info" -}}
ericsson.com/product-name: "Microservice Chassis"
ericsson.com/product-number: "CXP90001/2"
ericsson.com/product-revision: "{{ .Values.productInfo.rstate }}"
{{- end -}}

