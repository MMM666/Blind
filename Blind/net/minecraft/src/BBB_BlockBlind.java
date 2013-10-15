package net.minecraft.src;

public class BBB_BlockBlind extends Block {

	public static final int dBlindOpen	= 0x08;
	public static final int dBlindClose	= 0x00;
	
	public static int renderID = 0;


	protected BBB_BlockBlind(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {
		this.updateBlindBounds(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	public void updateBlindBounds(int par1) {
		float var3 = 0.125F;
		par1 = par1 & 0x07;
		if (par1 == 2) {
			this.setBlockBounds(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
		}
		
		if (par1 == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
		}
		
		if (par1 == 4) {
			this.setBlockBounds(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		
		if (par1 == 5) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
		}
	}

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5, float par6, float par7, float par8, int par9) {
		int lmeta = par9;
		lmeta = par5;
/*		
		if ((par9 == 0 || par5 == 2)
				&& par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
			lmeta = 2;
		}
		
		if ((par9 == 0 || par5 == 3)
				&& par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			lmeta = 3;
		}
		
		if ((par9 == 0 || par5 == 4)
				&& par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			lmeta = 4;
		}
		
		if ((par9 == 0 || par5 == 5)
				&& par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
			lmeta = 5;
		}
*/		
		return lmeta;
	}


	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4,
			EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		setAroundMetaData(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4) ^ 0x08);
		if (!par1World.isRemote) {
			par1World.playSoundEffect(par2, par3, par4, stepSound.getPlaceSound(), 1.0F, 1.0F);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
//		int lmeta = par1World.getBlockMetadata(par2, par3, par4);
//		int lp = par1World.getBlockPowerInput(par2, par3, par4);
//		if (lp > 1) {
//			setAroundMetaData(par1World, par2, par3, par4, lmeta & 0x07 | dBlindOpen);
//		} else {
//			setAroundMetaData(par1World, par2, par3, par4, lmeta & 0x07 | dBlindClose);
//		}
	}

	public int getAroundMetaData(World pWorld, int px, int py, int pz, int pMeta) {
		int lmeta = pMeta & 0x07;
		switch (lmeta) {
		case 2:
		case 3:
			for (int ly = -1; ly < 2; ly++) {
				for (int lx = -1; lx < 2; lx++) {
					if (pWorld.getBlockId(px + lx, py + ly, pz) == blockID) {
						int lget = pWorld.getBlockMetadata(px + lx, py + ly, pz);
						if ((lget & 0x07) == lmeta) {
							return lget;
						}
					}
				}
			}
			break;
		case 4:
		case 5:
			for (int ly = -1; ly < 2; ly++) {
				for (int lz = -1; lz < 2; lz++) {
					if (pWorld.getBlockId(px, py + ly, pz + lz) == blockID) {
						int lget = pWorld.getBlockMetadata(px, py + ly, pz + lz);
						if ((lget & 0x07) == lmeta) {
							return lget;
						}
					}
				}
			}
			break;
		}
		return -1;
	}

	public void setMetaData(World pWorld, int px, int py, int pz, int pMeta) {
		if (pWorld.getBlockId(px, py, pz) == blockID) {
			int lmeta = pWorld.getBlockMetadata(px, py, pz);
			if ((pMeta != lmeta) && ((pMeta & 0x07) == (lmeta & 0x07))) {
				setAroundMetaData(pWorld, px, py, pz, pMeta);
			}
		}
	}

	public void setAroundMetaData(World pWorld, int px, int py, int pz, int pMeta) {
		int lmeta = pMeta & 0x07;
		pWorld.setBlockMetadataWithNotify(px, py, pz, pMeta, 2);
		switch (lmeta) {
		case 2:
		case 3:
			setMetaData(pWorld, px, py + 1, pz, pMeta);
			setMetaData(pWorld, px, py - 1, pz, pMeta);
			setMetaData(pWorld, px + 1, py, pz, pMeta);
			setMetaData(pWorld, px - 1, py, pz, pMeta);
			break;
		case 4:
		case 5:
			setMetaData(pWorld, px, py + 1, pz, pMeta);
			setMetaData(pWorld, px, py - 1, pz, pMeta);
			setMetaData(pWorld, px, py, pz + 1, pMeta);
			setMetaData(pWorld, px, py, pz - 1, pMeta);
			break;
		}
	}

}
