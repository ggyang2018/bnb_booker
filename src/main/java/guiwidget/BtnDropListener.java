package guiwidget;

/* *************************************************************
  Name: BtnDropListener.java
  Note: A listener as a target to drop a button
  *****************************************************  ***/

 // import java.awt.*;
  import java.awt.dnd.*;
//  import java.awt.datatransfer.*;

//import javax.swing.*;

  public class BtnDropListener implements DropTargetListener
  {
	  GFrame Frm;

	  public BtnDropListener(GFrame f)
	  {  Frm = f;  }

	  public void dragEnter(DropTargetDragEvent event)
	  {
		  //when mouse get the area, what's happen
		 // int a = event.getDropAction( );

		  if(!isDragAcceptable(event))
		  {
			  event.rejectDrag( );
			  return;
		  }
	  }

	  
      public void dragExit(DropTargetEvent event)
	  {
	  }

	  public void dragOver(DropTargetDragEvent event)
      {  //provide visual feedback here
	  }

	  public void dropActionChanged(DropTargetDragEvent event)
      {
		  if(!isDragAcceptable(event))
		  {
			  event.rejectDrag( );
			  return;
		  }
	  }

	  public void drop(DropTargetDropEvent event)
      {
		  if(!isDropAcceptable(event))
		  {
			  event.rejectDrop( );
			  return;
		  }

		  event.acceptDrop(DnDConstants.ACTION_COPY);

		//  Transferable transf = event.getTransferable( );
		//  DataFlavor[] flavors= transf.getTransferableDataFlavor();
	  }

	  public boolean isDragAcceptable(DropTargetDragEvent event)
	  {
		  //check data flavors here, acceept all
		  return (event.getDropAction( ) &
			     DnDConstants.ACTION_COPY_OR_MOVE)!=0;
	  }

	  public boolean isDropAcceptable(DropTargetDropEvent event)
	  {
		   return (event.getDropAction( ) &
			     DnDConstants.ACTION_COPY_OR_MOVE)!=0;
	  }
  }
