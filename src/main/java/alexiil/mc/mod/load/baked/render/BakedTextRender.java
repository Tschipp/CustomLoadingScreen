package alexiil.mc.mod.load.baked.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import alexiil.mc.mod.load.render.MinecraftDisplayerRenderer;

import buildcraft.lib.expression.api.IExpressionNode.INodeDouble;
import buildcraft.lib.expression.api.IExpressionNode.INodeLong;
import buildcraft.lib.expression.node.value.NodeVariableDouble;
import buildcraft.lib.expression.node.value.NodeVariableObject;

public abstract class BakedTextRender extends BakedRenderPositioned {
    protected final NodeVariableObject<String> varText;
    protected final INodeDouble x;
    protected final INodeDouble y;
    protected final INodeLong colour;
    protected final String fontTexture;
    private String _text;
    private int _width;
    private long _colour;
    private double _x, _y;

    public BakedTextRender(NodeVariableObject<String> varText, NodeVariableDouble varWidth, NodeVariableDouble varHeight, INodeDouble x, INodeDouble y, INodeLong colour, String fontTexture) {
        super(varWidth, varHeight);
        this.varText = varText;
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.fontTexture = fontTexture;
    }

    @Override
    public void evaluateVariables(MinecraftDisplayerRenderer renderer) {
        _text = getText();
        FontRenderer font = renderer.fontRenderer(fontTexture);
        _width = font.getStringWidth(_text);
        varWidth.value = _width;
        varHeight.value = font.FONT_HEIGHT;
        _x = x.evaluate();
        _y = y.evaluate();
        _colour = 0xFF_00_00_00 | colour.evaluate();
    }

    @Override
    public void render(MinecraftDisplayerRenderer renderer) {
        FontRenderer font = renderer.fontRenderer(fontTexture);
        font.drawString(_text, (float) _x, (float) _y, (int) _colour, false);
        GlStateManager.color(1, 1, 1, 1);
    }

    public abstract String getText();

    @Override
    public String getLocation() {
        return fontTexture;
    }
}
