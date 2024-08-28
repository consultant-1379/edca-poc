# Chart name
{{- define "eric-edca-integration-helm.chart" -}}
 {{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

# Object Storage MN is enabled
{{- define "object-storage-mn.enabled" -}}
  {{- if and (index .Values "eric-data-object-storage-mn") -}}
    {{- if eq (index .Values "eric-data-object-storage-mn" "enabled" | quote) "\"false\"" -}}
      false
    {{- else -}}
      true
    {{- end -}}
  {{- else -}}
      false
  {{- end -}}
{{- end -}}