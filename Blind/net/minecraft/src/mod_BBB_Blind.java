package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class mod_BBB_Blind extends BaseMod {

	public static boolean isDebugMessage = true;
	public static int BlockID = 225;

	public static Block blind;


	public static void debug(String str, Object...pArg) {
		if (isDebugMessage) {
			System.out.println(String.format("Blind-" + str, pArg));
		}
	}

	@Override
	public String getVersion() {
		return "1.6.2-1";
	}

	@Override
	public void load() {
		debug("Initialize.");
		if (BlockID > 0) {
			try {
				BBB_BlockBlind.renderID = ModLoader.getUniqueBlockModelID(this, false);
			} catch (Exception e) {
			} catch (Error e) {
			}
			blind = new BBB_BlockBlind(BlockID, Material.wood).setHardness(2.0F).setResistance(5F).setStepSound(Block.soundWoodFootstep)
					.setUnlocalizedName("blind").func_111022_d("blind").setCreativeTab(CreativeTabs.tabBlock);
			ModLoader.registerBlock(blind);
			ModLoader.addName(blind, "Blind");
			ModLoader.addName(blind, "ja_JP", "ƒuƒ‰ƒCƒ“ƒh");
			debug("Blind Enable.");
		}
	}

	@Override
	public boolean renderWorldBlock(RenderBlocks renderblocks,
			IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, i, j, k));
		int lmeta = iblockaccess.getBlockMetadata(i, j, k);
		Icon licon = block.getIcon(0, lmeta);
		
//		double lumin = (double)licon.getInterpolatedU(renderblocks.renderMinX * 16.0D);
//		double lumax = (double)licon.getInterpolatedU(renderblocks.renderMaxX * 16.0D);
//		double lvmin = (double)licon.getInterpolatedV(renderblocks.renderMinY * 16.0D);
//		double lvmax = (double)licon.getInterpolatedV(renderblocks.renderMaxY * 16.0D);
		double lumin = (double)licon.getInterpolatedU(0D * 16.0D);
		double lumax = (double)licon.getInterpolatedU(1D * 16.0D);
		double lvmin = (double)licon.getInterpolatedV(0D * 16.0D);
		double lvmax = (double)licon.getInterpolatedV(1D * 16.0D);

		if (renderblocks.renderMinX < 0.0D || renderblocks.renderMaxX > 1.0D) {
			lumin = (double)licon.getMinU();
			lumax = (double)licon.getMaxU();
		}

		if (renderblocks.renderMinZ < 0.0D || renderblocks.renderMaxZ > 1.0D) {
			lvmin = (double)licon.getMinV();
			lvmax = (double)licon.getMaxV();
		}
		boolean lopen = (lmeta & 0x08) != 0;
		double lvdelta = (lvmax - lvmin) / 8D;
		double lv = lvmin;
		double lrx = Math.cos(lopen ? 0.3D : 1.4D) * 0.125D;
		double lry = Math.sin(lopen ? 0.3D : 1.4D) * 0.125D;
		lmeta = lmeta & 0x07;
		
		for (double ly = 1D; ly > 0D; ly -= 0.125D) {
			tessellator.setColorRGBA(255, 255, 255, 255);
			switch (lmeta) {
			case 2:
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly, (double) k + 0.875D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly, (double) k + 0.875D, lumin, lv);
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly - lry, (double) k + 0.875D + lrx, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly - lry, (double) k + 0.875D + lrx, lumax, lv + lvdelta);
				
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly, (double) k + 0.875D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly, (double) k + 0.875D, lumin ,lv);
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly - lry, (double) k + 0.875D + lrx, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly - lry, (double) k + 0.875D + lrx, lumax, lv + lvdelta);
				break;
			case 3:
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly, (double) k + 0.125D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly, (double) k + 0.125D, lumin, lv);
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly - lry, (double) k + 0.125D - lrx, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly - lry, (double) k + 0.125D - lrx, lumax, lv + lvdelta);
				
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly, (double) k + 0.125D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly, (double) k + 0.125D, lumin ,lv);
				tessellator.addVertexWithUV((double) i + 0D, (double) j + ly - lry, (double) k + 0.125D - lrx, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 1D, (double) j + ly - lry, (double) k + 0.125D - lrx, lumax, lv + lvdelta);
				break;
			case 4:
				tessellator.addVertexWithUV((double) i + 0.875D, (double) j + ly, (double) k + 0D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 0.875D, (double) j + ly, (double) k + 1D, lumin, lv);
				tessellator.addVertexWithUV((double) i + 0.875D + lrx, (double) j + ly - lry, (double) k + 1D, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 0.875D + lrx, (double) j + ly - lry, (double) k + 0D, lumax, lv + lvdelta);
				
				tessellator.addVertexWithUV((double) i + 0.875D, (double) j + ly, (double) k + 1D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 0.875D, (double) j + ly, (double) k + 0D, lumin ,lv);
				tessellator.addVertexWithUV((double) i + 0.875D + lrx, (double) j + ly - lry, (double) k + 0D, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 0.875D + lrx, (double) j + ly - lry, (double) k + 1D, lumax, lv + lvdelta);
				break;
			case 5:
				tessellator.addVertexWithUV((double) i + 0.125D, (double) j + ly, (double) k + 0D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 0.125D, (double) j + ly, (double) k + 1D, lumin, lv);
				tessellator.addVertexWithUV((double) i + 0.125D - lrx, (double) j + ly - lry, (double) k + 1D, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 0.125D - lrx, (double) j + ly - lry, (double) k + 0D, lumax, lv + lvdelta);
				
				tessellator.addVertexWithUV((double) i + 0.125D, (double) j + ly, (double) k + 1D, lumax, lv);
				tessellator.addVertexWithUV((double) i + 0.125D, (double) j + ly, (double) k + 0D, lumin ,lv);
				tessellator.addVertexWithUV((double) i + 0.125D - lrx, (double) j + ly - lry, (double) k + 0D, lumin, lv + lvdelta);
				tessellator.addVertexWithUV((double) i + 0.125D - lrx, (double) j + ly - lry, (double) k + 1D, lumax, lv + lvdelta);
				break;
			}
			lv += lvdelta;
		}
		
		return true;
	}

	@Override
	public void renderInvBlock(RenderBlocks renderblocks, Block block, int i, int j) {
		// TODO Auto-generated method stub
		super.renderInvBlock(renderblocks, block, i, j);
	}

}
