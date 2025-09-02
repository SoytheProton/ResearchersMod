package researchersmod.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class VerticalAura2Effect extends AbstractGameEffect {
    private static final float NUM_PARTICLES = 20.0F;

    private float x;

    private float y;

    public VerticalAura2Effect(Color c, float x, float y) {
        this.color = c;
        this.x = x;
        this.y = y;
    }

    public void update() {
        for (int i = 0; i < 10.0F; i++)
            AbstractDungeon.effectsQueue.add(new VerticalAura2Particle(this.color, this.x, this.y));
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}
