package de.lessvoid.nifty.internal.canvas;

import de.lessvoid.nifty.api.NiftyColor;
import de.lessvoid.nifty.api.NiftyLinearGradient;
import de.lessvoid.nifty.internal.math.Mat4;
import de.lessvoid.nifty.spi.NiftyRenderDevice;
import de.lessvoid.nifty.spi.NiftyTexture;

public class Context {
  private final NiftyTexture texture;
  private NiftyColor fillColor;
  private float lineWidth;
  private NiftyLinearGradient linearGradient;
  private NiftyColor strokeStyle = NiftyColor.WHITE();
  private NiftyColor textColor = NiftyColor.WHITE();
  private float textSize = 1.f;
  private Mat4 transform = Mat4.createIdentity();

  public Context(final NiftyTexture textureParam) {
    texture = textureParam;
  }

  public void prepare(final NiftyRenderDevice renderDevice) {
    fillColor = NiftyColor.BLACK();
    linearGradient = null;
    renderDevice.beginRenderToTexture(texture);
  }

  public void flush(final NiftyRenderDevice renderDevice) {
    renderDevice.endRenderToTexture(texture);
  }

  public void setFillColor(final NiftyColor color) {
    fillColor = new NiftyColor(color);
    linearGradient = null;
  }

  public NiftyColor getFillColor() {
    return fillColor;
  }

  public void setFillLinearGradient(final NiftyLinearGradient gradient) {
    fillColor = null;
    linearGradient = new NiftyLinearGradient(gradient);
  }

  public void setLineWidth(final float lineWidth) {
    this.lineWidth = lineWidth;
  }

  public float getLineWidth() {
    return lineWidth;
  }

  public NiftyTexture getNiftyTexture() {
    return texture;
  }

  public NiftyLinearGradient getFillLinearGradient() {
    return linearGradient;
  }

  public void setStrokeStyle(final NiftyColor color) {
    this.strokeStyle = color;
  }

  public NiftyColor getStrokeStyle() {
    return strokeStyle;
  }

  public void setTextSize(final float textSize) {
    this.textSize = textSize;
  }

  public float getTextSize() {
    return textSize;
  }

  public void setTextColor(final NiftyColor textColor) {
    this.textColor = textColor;
  }

  public NiftyColor getTextColor() {
    return textColor;
  }

  public void addTransform(final Mat4 mat) {
    transform = Mat4.mul(transform, mat);
  }

  public void resetTransform() {
    transform = Mat4.createIdentity();
  }

  public Mat4 getTransform() {
    return transform;
  }
}
