/** munter - das mobile unterrichtsbegleitende Unterstützungssystem für angehende Lehrpersonen
© 2020 Herrmann Elfreich

This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, on
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

package core;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Sequence implements Serializable {
	private int id;
	private String title;
	private String subject;
	private int grade;
	private String preknowledge = "";
	private String goal = "";
	private int standard;
	private String comments = "";
	private String beschreibung = "";

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Sequence() {}

	public Sequence(int id, String title, String subject, int grade, String preknowledge, int standard) {
		this.id = id;
		this.title = title;
		this.subject = subject;
		this.grade = grade;
		this.preknowledge = preknowledge;
		this.standard = standard;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPreknowledge() {
		return preknowledge;
	}

	public void setPreknowledge(String preknowledge) {
		this.preknowledge = preknowledge;
	}

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

}
