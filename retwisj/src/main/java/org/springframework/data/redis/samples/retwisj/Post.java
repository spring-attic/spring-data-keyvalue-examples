/*
 * Copyright 2011 the original uid or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.redis.samples.retwisj;


/**
 * Class describing a post.
 * 
 * @author Costin Leau
 */
public class Post {

	private String content;
	private String uid;
	private String time = String.valueOf(System.currentTimeMillis());
	private String replyPid;
	private String replyUid;

	/**
	 * Returns the content.
	 *
	 * @return Returns the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * Returns the uid.
	 *
	 * @return Returns the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid The uid to set.
	 */
	public void setUid(String author) {
		this.uid = author;
	}

	/**
	 * Returns the time.
	 *
	 * @return Returns the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time The time to set.
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Returns the replyPid.
	 *
	 * @return Returns the replyPid
	 */
	public String getReplyPid() {
		return replyPid;
	}

	/**
	 * @param replyPid The replyPid to set.
	 */
	public void setReplyPid(String replyPid) {
		this.replyPid = replyPid;
	}

	/**
	 * Returns the replyUid.
	 *
	 * @return Returns the replyUid
	 */
	public String getReplyUid() {
		return replyUid;
	}

	/**
	 * @param replyUid The replyUid to set.
	 */
	public void setReplyUid(String replyUid) {
		this.replyUid = replyUid;
	}

	@Override
	public String toString() {
		return "Post [content=" + content + ", replyPid=" + replyPid + ", replyUid=" + replyUid
				+ ", time=" + time + ", uid=" + uid + "]";
	}
}