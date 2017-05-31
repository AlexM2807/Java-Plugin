package preh.alexmaftei.editor.views;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;



import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import TreeModel.Model;
import TreeModel.TreeElement;
import TreeUI.MovingBoxContentProvider;
import TreeUI.MovingBoxLabelProvider;
import paresers.FileGenerator;
import paresers.ParsConfig;
import paresers.ReadFromXML;
import paresers.TreeMaker;
import paresers.WriteInXML;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class Editor extends ViewPart
{

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "preh.alexmaftei.editor.views.Editor";
	public static final String ID2 = "preh.alexmaftei.editor.views.Content";
	private Model model;
	public static String configARXML = null;
	public static String path = null;
	private Button btnConfig;

	// private String path = "/Users/alexandrumaftei/Desktop/Modules";

	private LinkedHashMap<String, String> treeHashList;
	private ViewSetter viewSetter;
	protected MovingBoxLabelProvider labelProvider;
	private TreeViewer treeViewer;
	private String itemType;
	private String selectedDir = null;
	private Button btnModulePath;
	Shell shell = new Shell();
	private  File folder;
	private List<String> files;

	/*
	 * We will set up a dummy model to initialize tree heararchy. In a real
	 * code, you will connect to a real model and expose its hierarchy.
	 */
	public void initialize()
	{

	}

	/**
	 * The constructor.
	 */
	public Editor()
	{
		
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */

	public void createPartControl(Composite parent)
	{

		if (configARXML == null)
		{
			
			btnConfig = new Button(parent, SWT.NONE);
			btnConfig.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{

					FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);

					fileDialog.setFilterPath(configARXML);
					fileDialog.setFilterExtensions(new String[] { "*.arxml" });
					fileDialog.setFilterNames(new String[] { "ARXML format" });

					String firstFile = fileDialog.open();
					if (firstFile != null)
					{
						fileDialog.getFilterPath();
						String[] selectedFiles = fileDialog.getFileNames();
						StringBuffer sb = new StringBuffer(fileDialog.getFilterPath() + File.separator);
						for (int i = 0; i < selectedFiles.length; i++)
						{
							sb.append(selectedFiles[i]);
						}

						configARXML = sb.toString();
						btnConfig.dispose();

						
						createPartControl(parent);
						parent.layout();
					}
				}
			});

			btnConfig.setText("Select Config File");
		}
		else if (path == null)
		{
			
			btnModulePath = new Button(parent, SWT.NONE);
			btnModulePath.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{

					DirectoryDialog directoryDialog = new DirectoryDialog(shell);

					directoryDialog.setFilterPath(selectedDir);
					directoryDialog.setMessage("Please select modules directory and click OK");

					String dir = directoryDialog.open();
					if (dir != null)
					{
						path = dir;

						btnModulePath.dispose();
						createPartControl(parent);
						
						parent.layout();

					}
				}
			});
			btnModulePath.setText("Select Module Path");
		}

		else
		{
			
			//parent.layout();
			treeViewer = new TreeViewer(parent);
			

			treeViewer.setContentProvider(new MovingBoxContentProvider());
			treeViewer.setLabelProvider(new MovingBoxLabelProvider());
			treeViewer.setInput(getInitalInput());

			labelProvider = new MovingBoxLabelProvider();
			treeViewer.setLabelProvider(labelProvider);

			treeViewer.addSelectionChangedListener(new ISelectionChangedListener()
			{
				public void selectionChanged(SelectionChangedEvent event)
				{

					// if the selection is empty clear the label
					if (event.getSelection().isEmpty())
					{

						return;
					}
					if (event.getSelection() instanceof IStructuredSelection)
					{
						IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

						for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();)
						{

							model = (Model) iterator.next();

							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							IViewPart view = null;
							try
							{
								view = page.showView(ID2);
							}
							catch (PartInitException e1)
							{

								e1.printStackTrace();
							}

							ReadFromXML rXML = new ReadFromXML();

							viewSetter = (ViewSetter) view;
							// viewSetter.sendTarget(model.getFileName(),
							// model.getShortName() , model.getType());
							viewSetter.sedModel(model);
							treeHashList = rXML.takeParameterToHashMap(model.getFileName(), model.getShortName());
							viewSetter.sendHashMap(treeHashList);

						}

					}
				}

			});
			
			
			Tree tree = treeViewer.getTree();
			ParsConfig parsConfig = new ParsConfig();
			tree.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseDown(MouseEvent e)
				{
					if (e.button == 3)
					{
						
						IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

						List<?> list = null;

						if (selection.isEmpty())
						{
							
							itemType = "file";
							list = parsConfig.findAllModules();
						}

						else
						{
					
							for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();)
							{

									itemType = "container";

								
								Model model = (Model) iterator.next();
								list = parsConfig.takeValuesForPopUpMenu(model.getType());

							}
						}
						popUpMenu(parent, list, itemType);

					}
				}
			});
		}

	}

	public TreeElement getInitalInput()
	{

		 folder = new File(path);
		files = listFilesForFolder(folder);
		TreeMaker TM = new TreeMaker();

		TreeElement root = new TreeElement("root");
		for (int i = 0; i < files.size(); i++)
		{
			
			TreeElement treeElem = TM.takeTreeValues(path, files.get(i));
			root.add(treeElem);
		}

		return root;
	}

	private void popUpMenu(Composite parent, List<?> list, String itemType)
	{

		Menu popupMenu = new Menu(parent);
		MenuItem newItem = new MenuItem(popupMenu, SWT.CASCADE);
		newItem.setText("New");
		MenuItem refreshItem = new MenuItem(popupMenu, SWT.NONE);
		refreshItem.setText("Refresh");
		refreshItem.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				
				treeViewer.setInput(getInitalInput());
			
			}
		});

		MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
		deleteItem.setText("Delete");
		
		deleteItem.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				if (model.getCategory().equals("file"))
				{
					FileGenerator fileGenerator = new FileGenerator();
					fileGenerator.deleteFile(folder, model.getFileName());
				}
				else
				{
				
				WriteInXML wXML = new WriteInXML();
				wXML.deleteNode(model.getFileName(), model.getShortName());
				}
				treeViewer.setInput(getInitalInput());
			;
				
			}
		});
		
		
		Menu newMenu = new Menu(popupMenu);
		newItem.setMenu(newMenu);

		for (int i = 0; i < list.size(); i++)
		{
			MenuItem item = new MenuItem(newMenu, SWT.PUSH);
			item.setText(list.get(i).toString());
			item.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent e)
				{
					if (itemType.equals("file"))
					{
						FileGenerator fileGenerator = new FileGenerator();
						fileGenerator.createFile(item.getText(), "UUID TEST");
						
						
					}
					else if (itemType.equals("container"))
					{
						WriteInXML wXML = new WriteInXML();
						if (wXML.checkForDuplicate(model.getFileName(), item.getText()) == false)
							wXML.createEcucContainerValue(model.getFileName(), model.getShortName(), item.getText(),
									model.getDefinitionRef() + item.getText());
					}
					treeViewer.setInput(getInitalInput());

				}
			});

		}

		parent.setMenu(popupMenu);

		parent.layout();
		parent.layout(true);

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{
		// viewer.getControl().setFocus();
	}

	public List<String> listFilesForFolder(final File folder)
	{

		List<String> files = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles())
		{

				if (fileEntry.getName().toString().contains(".arxml"))
				{
					files.add(fileEntry.getName().toString().replaceFirst(".arxml", ""));
				}

		}
		return files;
	}
	
	

}
