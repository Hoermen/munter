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
public class Lesson implements Serializable {
	private int id;
	private String title;
	private int length;
	private int order;
	private int sequenceid;
	private String goal = "";
	private String homeworks = "";
	private String comments = "";
	private String beschreibung = "";
	private String feedback = "";


	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Lesson() {}

	public Lesson(int id, String title, int length, int order, int sequenceid) {
		this.id = id;
		this.title = title;
		this.length = length;
		this.order = order;
		this.sequenceid = sequenceid;
	}

	public int getSequenceid() {
		return sequenceid;
	}

	public void setSequenceid(int sequenceid) {
		this.sequenceid = sequenceid;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the homeworks
	 */
	public String getHomeworks() {
		return homeworks;
	}

	/**
	 * @param homeworks the homeworks to set
	 */
	public void setHomeworks(String homeworks) {
		this.homeworks = homeworks;
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
