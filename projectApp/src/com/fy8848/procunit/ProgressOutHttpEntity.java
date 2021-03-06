﻿package com.fy8848.procunit;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;



/**
 * ProgressOutHttpEntity：输出流(OutputStream)时记录已发送字节数
 * 
 * @author Cow
 * 
 */
public class ProgressOutHttpEntity extends HttpEntityWrapper {

	private final ProgressListener listener;
	private long FiTotal=0;
	private int FiPercent=0;

	public ProgressOutHttpEntity(final HttpEntity entity,final ProgressListener listener,long iTotal) {
		super(entity);
		this.listener = listener;
		FiTotal=iTotal;
	}

	public static class CountingOutputStream extends FilterOutputStream {

		private final ProgressListener listener;
		private long transferred;
		private long FiTotal;

		CountingOutputStream(final OutputStream out,
				final ProgressListener listener,long iTotal) {
			super(out);
			this.listener = listener;
			this.transferred = 0;
			this.FiTotal=iTotal;
		}
		
		protected int calPercent()
		{
			
			if(FiTotal>0) return (int) (100 * transferred / FiTotal);
			else return 0;
		}

		@Override
		public void write(final byte[] b, final int off, final int len)
				throws IOException {
			// NO, double-counting, as super.write(byte[], int, int)
			// delegates to write(int).
			// super.write(b, off, len);
			out.write(b, off, len);
			this.transferred += len;
			this.listener.transferred(calPercent());
		}

		@Override
		public void write(final int b) throws IOException {
			out.write(b);
			this.transferred++;
			this.listener.transferred(calPercent());
		}

	}

	@Override
	public void writeTo(final OutputStream out) throws IOException {
		this.wrappedEntity.writeTo(out instanceof CountingOutputStream ? out
				: new CountingOutputStream(out, this.listener,this.FiTotal));
	}
}