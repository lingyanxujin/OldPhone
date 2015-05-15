package com.tan.oldphone.model;
public class ChannelJson extends MessageJson {
	private Data data;

	public static class Data {
		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		private String channel;

	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
