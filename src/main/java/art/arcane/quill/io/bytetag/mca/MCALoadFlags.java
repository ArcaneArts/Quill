/*
 * This file is part of Quill by Arcane Arts.
 *
 * Quill by Arcane Arts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Quill by Arcane Arts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License in this package for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quill.  If not, see <https://www.gnu.org/licenses/>.
 */

package art.arcane.quill.io.bytetag.mca;

public class MCALoadFlags {

    public static long BIOMES = 0x0001;
    public static long HEIGHTMAPS = 0x0002;
    public static long CARVING_MASKS = 0x0004;
    public static long ENTITIES = 0x0008;
    public static long TILE_ENTITIES = 0x0010;
    public static long TILE_TICKS = 0x0040;
    public static long LIQUID_TICKS = 0x0080;
    public static long TO_BE_TICKED = 0x0100;
    public static long POST_PROCESSING = 0x0200;
    public static long STRUCTURES = 0x0400;
    public static long BLOCK_LIGHTS = 0x0800;
    public static long BLOCK_STATES = 0x1000;
    public static long SKY_LIGHT = 0x2000;
    public static long LIGHTS = 0x4000;
    public static long LIQUIDS_TO_BE_TICKED = 0x8000;

    public static long ALL_DATA = 0xffffffffffffffffL;


}
