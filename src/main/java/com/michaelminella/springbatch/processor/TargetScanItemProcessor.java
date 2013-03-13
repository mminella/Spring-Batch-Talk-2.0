package com.michaelminella.springbatch.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.springframework.batch.item.ItemProcessor;

import com.michaelminella.springbatch.domain.Target;

public class TargetScanItemProcessor implements ItemProcessor<Target, Target> {

	@Override
	public Target process(Target curTarget) throws Exception {
		try {
			System.out.println("About to check: " + curTarget.getIp() + ":" + curTarget.getPort() + " on thread " + Thread.currentThread().getName());
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(curTarget.getIp(), curTarget.getPort()), 1000);
			socket.setSoTimeout(1000);
			curTarget.setConnected(true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			StringBuilder bannerBuffer = new StringBuilder();
			String banner;

			while ((banner = input.readLine()) != null) {
				System.out.println("curLine = |" + banner + "|");
				bannerBuffer.append(banner);
			}

			curTarget.setBanner(bannerBuffer.toString());

			input.close();
			socket.close();
		} catch (SocketTimeoutException ignore) {
			System.out.println("The port " + curTarget.getPort() + " is closed.");
		} catch (ConnectException ignore) {
			System.out.println("The connection was refused on port " + curTarget.getPort());
		} catch (Throwable error) {
			System.out.println("An exception was thrown: " + error.getClass() + " with the message " + error.getMessage());
		}

		return curTarget;
	}
}