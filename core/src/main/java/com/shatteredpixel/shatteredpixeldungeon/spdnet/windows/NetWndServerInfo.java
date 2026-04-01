package com.shatteredpixel.shatteredpixeldungeon.spdnet.windows;

import static com.watabou.utils.DeviceCompat.isDebug;
import static com.watabou.utils.DeviceCompat.isDesktop;

import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.NetSettings;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.ui.BlueButton;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.ui.NetIcons;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.utils.LanScanner;
import com.shatteredpixel.shatteredpixeldungeon.spdnetbutcopy.ui.SPDNetTextInput;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.Net;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.watabou.input.PointerEvent;
import com.watabou.noosa.PointerArea;

import java.util.List;

public class NetWndServerInfo extends NetWindow {
	private static final int WIDTH_P = 122;
	private static final int WIDTH_L = 223;
	private static final int BTN_HEIGHT = 18;
	private static final int INPUT_HEIGHT = 16;

	private static final float GAP = 2;

	IconTitle title;
	RenderedTextBlock host;
	RenderedTextBlock status;
	RenderedTextBlock scanStatus;
	BlueButton keyBtn;
	BlueButton connectBtn;
	BlueButton scanBtn;
	BlueButton saveServerBtn;
	BlueButton clearServerBtn;

	SPDNetTextInput ipInput;
	SPDNetTextInput portInput;

	NetWndServerInfo self = this;

	int zoom = PixelScene.defaultZoom;

	public NetWndServerInfo() {
		super();

		// 初始化socket客户端以正确显示服务器地址
		Net.getSocket();

		int height, y = 0;

		int maxWidth = PixelScene.landscape() ? WIDTH_L : WIDTH_P;

		title = new IconTitle(NetIcons.get(NetIcons.GLOBE), Messages.get(this, "server_connection"));
		title.setRect(0, 0, maxWidth, 20);
		add(title);

		float bottom = y;
		bottom = title.bottom();

		if (isDesktop() && isDebug()) {
			host = PixelScene.renderTextBlock("服务器地址(调试)" + "\n" + Net.getServerUrl(), 7);
		} else {
			host = PixelScene.renderTextBlock(Messages.get(this, "server_address")+"\n" + Net.getServerUrl(), 9);
		}
		host.maxWidth(maxWidth);
		host.setPos(0, bottom + GAP);
		add(host);

		bottom = host.bottom() + GAP;

		status = new RenderedTextBlock(Net.isConnected() ? Messages.get(this, "connected") : Messages.get(this, "disconnected"), 9 * zoom) {
			@Override
			public synchronized void update() {
				super.update();
				text(Net.isConnected() ? Messages.get(NetWndServerInfo.class, "connected") : Messages.get(NetWndServerInfo.class, "disconnected"));
				hardlight(Net.isConnected() ? 0x00FF00 : 0xFF0000);
			}
		};

		status.zoom(1 / (float) zoom);
		status.setRect(0, bottom + GAP, maxWidth, 20);
		add(status);

		bottom = status.bottom() + (GAP * 3);

		// 添加自定义服务器设置区域
		RenderedTextBlock customServerLabel = PixelScene.renderTextBlock(Messages.get(this, "custom_server"), 8);
		customServerLabel.maxWidth(maxWidth);
		customServerLabel.hardlight(0xFFFFAA);
		customServerLabel.setPos(0, bottom);
		add(customServerLabel);
		bottom = customServerLabel.bottom() + GAP;

		// IP输入框
		RenderedTextBlock ipLabel = PixelScene.renderTextBlock(Messages.get(this, "ip_address"), 7);
		ipLabel.maxWidth((int)(maxWidth / 2 - GAP));
		ipLabel.setPos(0, bottom);
		add(ipLabel);

		RenderedTextBlock portLabel = PixelScene.renderTextBlock(Messages.get(this, "port"), 7);
		portLabel.maxWidth((int)(maxWidth / 2 - GAP));
		portLabel.setPos(maxWidth / 2 + GAP, bottom);
		add(portLabel);

		bottom = ipLabel.bottom() + GAP;

		float inputWidth = maxWidth / 2 - GAP;
		int textSize = (int) PixelScene.uiCamera.zoom * 8;

		// IP地址输入
		ipInput = new SPDNetTextInput(Chrome.get(Chrome.Type.TOAST_WHITE), false, textSize) {
			@Override
			public void enterPressed() {
				portInput.setActive(true);
			}

			@Override
			public void onActivated() {
				if (portInput != null) {
					portInput.setActive(false);
				}
			}
		};
		ipInput.setText(NetSettings.getServerUrl());
		ipInput.setMaxLength(50);
		add(ipInput);

		PointerArea ipClickArea = new PointerArea(0, 0, 0, 0) {
			@Override
			protected void onClick(PointerEvent event) {
				ipInput.setActive(true);
			}
		};
		add(ipClickArea);

		// 端口输入
		portInput = new SPDNetTextInput(Chrome.get(Chrome.Type.TOAST_WHITE), false, textSize) {
			@Override
			public void enterPressed() {
				saveServer();
			}

			@Override
			public void onActivated() {
				if (ipInput != null) {
					ipInput.setActive(false);
				}
			}
		};
		portInput.setText(NetSettings.getServerPort());
		portInput.setMaxLength(5);
		add(portInput);

		PointerArea portClickArea = new PointerArea(0, 0, 0, 0) {
			@Override
			protected void onClick(PointerEvent event) {
				portInput.setActive(true);
			}
		};
		add(portClickArea);

		bottom += INPUT_HEIGHT + GAP;

		// 扫描状态显示
		scanStatus = PixelScene.renderTextBlock("", 7);
		scanStatus.maxWidth(maxWidth);
		scanStatus.setPos(0, bottom);
		add(scanStatus);
		bottom = scanStatus.bottom() + GAP;

		// 局域网扫描按钮
		scanBtn = new BlueButton(Messages.get(this, "scan_lan")) {
			@Override
			protected void onClick() {
				startLanScan();
			}
		};
		add(scanBtn);
		scanBtn.setSize(maxWidth / 2 - GAP, BTN_HEIGHT);
		scanBtn.setPos(0, bottom);

		// 保存服务器按钮
		saveServerBtn = new BlueButton(Messages.get(this, "save_server")) {
			@Override
			protected void onClick() {
				saveServer();
			}
		};
		add(saveServerBtn);
		saveServerBtn.setSize(maxWidth / 2 - GAP, BTN_HEIGHT);
		saveServerBtn.setPos(maxWidth / 2 + GAP, bottom);

		bottom = saveServerBtn.bottom() + GAP;

		// 清除保存的服务器
		clearServerBtn = new BlueButton(Messages.get(this, "clear_server")) {
			@Override
			protected void onClick() {
				clearServer();
			}
		};
		add(clearServerBtn);
		clearServerBtn.setSize(maxWidth, BTN_HEIGHT);
		clearServerBtn.setPos(0, bottom);

		bottom = clearServerBtn.bottom() + GAP * 2;

		// 凭据按钮
		keyBtn = new BlueButton(Messages.get(this, "set_credentials")) {
			@Override
			protected void onClick() {
				NetWindow.showLogin();
			}
		};
		add(keyBtn);
		keyBtn.setSize(maxWidth / 2, BTN_HEIGHT);
		keyBtn.setPos(0, bottom);

		float finalBottom = bottom;
		connectBtn = new BlueButton(Messages.get(NetWndServerInfo.class, "connect")) {
			@Override
			public synchronized void update() {
				super.update();
				text.text(Net.isConnected() ? Messages.get(NetWndServerInfo.class, "disconnect") : Messages.get(NetWndServerInfo.class, "connect"));
				connectBtn.setRect(keyBtn.right(), finalBottom, maxWidth / 2, BTN_HEIGHT);
			}

			@Override
			protected void onClick() {
				super.onClick();
				Net.toggleSocket();
			}
		};
		add(connectBtn);
		connectBtn.setSize(maxWidth / 2, BTN_HEIGHT);
		connectBtn.setPos(keyBtn.right(), bottom);

		height = (int) (connectBtn.bottom() + GAP / 2);

		resize(maxWidth, height);

		// 设置输入框位置
		float inputTop = customServerLabel.bottom() + GAP * 2 + 7;
		ipInput.setRect(0, inputTop, inputWidth, INPUT_HEIGHT);
		ipClickArea.x = 0;
		ipClickArea.y = inputTop;
		ipClickArea.width = inputWidth;
		ipClickArea.height = INPUT_HEIGHT;

		portInput.setRect(maxWidth / 2 + GAP, inputTop, inputWidth, INPUT_HEIGHT);
		portClickArea.x = maxWidth / 2 + GAP;
		portClickArea.y = inputTop;
		portClickArea.width = inputWidth;
		portClickArea.height = INPUT_HEIGHT;
	}

	private void saveServer() {
		String ip = ipInput.getText().trim();
		String port = portInput.getText().trim();

		if (ip.isEmpty()) {
			NetWindow.error(Messages.get(this, "error"), Messages.get(this, "ip_empty"));
			return;
		}

		if (port.isEmpty()) {
			port = "65535";
		}

		// 验证端口
		try {
			int portNum = Integer.parseInt(port);
			if (portNum <= 0 || portNum > 65535) {
				NetWindow.error(Messages.get(this, "error"), Messages.get(this, "invalid_port"));
				return;
			}
		} catch (NumberFormatException e) {
			NetWindow.error(Messages.get(this, "error"), Messages.get(this, "invalid_port"));
			return;
		}

		NetSettings.setServerUrl(ip);
		NetSettings.setServerPort(port);

		// 构建完整URL并设置
		String serverUrl = "http://" + ip + ":" + port + "/spdnet";
		Net.setServerUrl(serverUrl);
		Net.destroySocket();

		// 刷新显示
		host.text(Messages.get(this, "server_address") + "\n" + serverUrl);

		NetWindow.info(Messages.get(this, "server_saved"));
	}

	private void clearServer() {
		NetSettings.setServerUrl("");
		NetSettings.setServerPort("65535");
		ipInput.setText("");
		portInput.setText("65535");

		// 重置为默认URL
		Net.refreshServerUrl();
		Net.destroySocket();

		// 刷新显示
		host.text(Messages.get(this, "server_address") + "\n" + Net.getServerUrl());

		NetWindow.info(Messages.get(this, "server_cleared"));
	}

	private void startLanScan() {
		scanBtn.enable(false);
		scanStatus.text(Messages.get(this, "scanning"));

		// 先尝试快速扫描
		LanScanner.quickScan(65535, new LanScanner.ScanCallback() {
			@Override
			public void onServerFound(String serverUrl) {
				// 显示找到的服务器
				scanStatus.text(Messages.get(self, "found") + "\n" + serverUrl);
				// 自动填充到输入框
				String ip = serverUrl.replace("http://", "").replace(":65535/spdnet", "");
				ipInput.setText(ip);
				portInput.setText("65535");
			}

			@Override
			public void onScanComplete(List<String> servers) {
				if (servers.isEmpty()) {
					scanStatus.text(Messages.get(self, "no_server"));
				} else {
					scanStatus.text(Messages.get(self, "scan_complete") + ": " + servers.size());
				}
				scanBtn.enable(true);
			}

			@Override
			public void onProgress(int scanned, int total) {
				// 可以显示进度
			}
		});
	}
}