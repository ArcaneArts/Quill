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

package art.arcane.quill.math;

public class AxisAlignedBB {
    private double xa;
    private double xb;
    private double ya;
    private double yb;
    private double za;
    private double zb;

    public AxisAlignedBB(double xa, double xb, double ya, double yb, double za, double zb) {
        this.xa = xa;
        this.xb = xb;
        this.ya = ya;
        this.yb = yb;
        this.za = za;
        this.zb = zb;
    }

    public AxisAlignedBB(AlignedPoint a, AlignedPoint b) {
        this(a.getX(), b.getX(), a.getY(), b.getY(), a.getZ(), b.getZ());
    }

    public boolean contains(AlignedPoint p) {
        return p.getX() >= xa && p.getX() <= xb && p.getY() >= ya && p.getZ() <= yb && p.getZ() >= za && p.getZ() <= zb;
    }

    public boolean intersects(AxisAlignedBB s) {
        return this.xb >= s.xa && this.yb >= s.ya && this.zb >= s.za && s.xb >= this.xa && s.yb >= this.ya && s.zb >= this.za;
    }
}
