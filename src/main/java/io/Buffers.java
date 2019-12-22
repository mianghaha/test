package io;

import java.nio.ByteBuffer;

public class Buffers {
	
	ByteBuffer readBuffer;
    ByteBuffer writeBuffer;
    String result;
     
    public Buffers(int readCapacity, int writeCapacity){
        readBuffer = ByteBuffer.allocate(readCapacity);
        writeBuffer = ByteBuffer.allocate(writeCapacity);
    }
     
    public ByteBuffer getReadBuffer(){
        return readBuffer;
    }
     
    public ByteBuffer getWriteBuffer(){
        return writeBuffer;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
