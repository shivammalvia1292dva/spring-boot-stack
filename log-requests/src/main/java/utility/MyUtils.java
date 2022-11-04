package utility;

import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

@Configuration
public class MyUtils {
	Resource resource = Resource.getDefault()
			  .merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "logical-service-name")));

			SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
			  .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().build()).build())
			  .setResource(resource)
			  .build();

			SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
			  .registerMetricReader(PeriodicMetricReader.builder(OtlpGrpcMetricExporter.builder().build()).build())
			  .setResource(resource)
			  .build();

			OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
			  .setTracerProvider(sdkTracerProvider)
			  .setMeterProvider(sdkMeterProvider)
			  .buildAndRegisterGlobal();
//			  .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
}
