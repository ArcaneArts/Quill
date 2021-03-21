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

package art.arcane.quill.maven;

import art.arcane.quill.format.Form;

import java.net.MalformedURLException;
import java.net.URL;

public class MavenArtifact {
    private final String group;
    private final String artifact;
    private final String version;

    public MavenArtifact(String group, String artifact, String version) {
        this.group = group;
        this.artifact = artifact;
        this.version = version;
    }

    public static MavenArtifact from(String group, String artifact, String version) {
        return new MavenArtifact(group, artifact, version);
    }

    public static MavenArtifact from(String a) {
        String[] gav = a.split("\\Q:\\E");

        return from(gav[0], gav[1], gav[2]);
    }

    public String toString() {
        return Form.split(":", group, artifact, version);
    }

    public String getGroup() {
        return group;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getVersion() {
        return version;
    }

    public URL toURL(MavenRepository repo) {
        String trimmer = repo.getRepository().trim();

        if (!trimmer.endsWith("/")) {
            trimmer += "/";
        }

        try {
            return new URL(trimmer + getGroup().replaceAll("\\Q.\\E", "/") + "/" + getArtifact() + "/" + getVersion() + "/" + getArtifact() + "-" + getVersion() + ".jar");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
