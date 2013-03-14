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
		BufferedReader input = null;
		Socket socket = null;
		try {
			System.out.println("About to check: " + curTarget.getIp() + ":" + curTarget.getPort() + " on thread " + Thread.currentThread().getName());
			socket = new Socket();
			socket.connect(new InetSocketAddress(curTarget.getIp(), curTarget.getPort()), 1000);
			socket.setSoTimeout(1000);
			curTarget.setConnected(true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String banner;

			while ((banner = input.readLine()) != null) {
				System.out.println("curLine = |" + banner + "|");
				if(curTarget.getBanner() != null) {
					curTarget.setBanner(curTarget.getBanner() + banner);
				} else {
					curTarget.setBanner(banner);
				}
			}
		} catch (SocketTimeoutException ignore) {
		} catch (ConnectException ignore) {
			System.out.println("The connection was refused on port " + curTarget.getPort());
		} catch (Throwable error) {
			System.out.println("An exception was thrown: " + error.getClass() + " with the message " + error.getMessage());
		} finally {
			if(input != null) {
				input.close();
			}

			if(socket != null) {
				socket.close();
			}
		}

		return curTarget;
	}
}