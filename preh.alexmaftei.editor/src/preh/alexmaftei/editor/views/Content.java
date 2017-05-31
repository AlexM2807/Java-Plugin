package preh.alexmaftei.editor.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import TreeModel.Model;
import paresers.ParsConfig;
import paresers.WriteInXML;
import preh.alexmaftei.editor.classs.Data;
import preh.alexmaftei.editor.classs.Parameters;

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
 * 
 * @param <T>
 */

public class Content extends ViewPart implements ViewSetter
{

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "preh.alexmaftei.editor.views.Editor";
	public static final String ID2 = "preh.alexmaftei.editor.views.Content";
	public static String configARXML = null;

	private Text textField;
	private Label label;
	Shell shell = new Shell();
	private Combo combo;

	private Spinner spinner;
	private Button button;
	private List<Parameters> listParameters;
	private HashMap<String, Combo> hashMapComboEnumeration;
	private HashMap<String, Combo> hashMapComboBoolean;
	private HashMap<String, Spinner> hashMapSpinner;
	private HashMap<String, Button> hashMapCheckBox;
	private Button checkBox;
	private List<Data> dataList;
	private String target;
	private String targetType;
	private String definitionRef;
	private List<String> list;

	private List<String> listShortName;

	private String fileName = null;

	private Composite parent;
	// private Composite container;

	/**
	 * The constructor.
	 */
	public Content()
	{
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent)
	{

//		if (configARXML == null)
//		{
//
//			btnConfig = new Button(parent, SWT.NONE);
//			btnConfig.addSelectionListener(new SelectionAdapter()
//			{
//				@Override
//				public void widgetSelected(SelectionEvent e)
//				{
//
//					FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
//
//					fileDialog.setFilterPath(configARXML);
//					fileDialog.setFilterExtensions(new String[] { "*.arxml" });
//					fileDialog.setFilterNames(new String[] { "ARXML format" });
//
//					String firstFile = fileDialog.open();
//					if (firstFile != null)
//					{
//						fileDialog.getFilterPath();
//						String[] selectedFiles = fileDialog.getFileNames();
//						StringBuffer sb = new StringBuffer(fileDialog.getFilterPath() + "/");
//						for (int i = 0; i < selectedFiles.length; i++)
//						{
//							sb.append(selectedFiles[i]);
//						}
//
//						configARXML = sb.toString();
//						btnConfig.dispose();
//
//						System.out.println(configARXML);
//						createPartControl(parent);
//
//					}
//				}
//			});
//
//			btnConfig.setText("Select Config File");
//		}
//		else
//		{

			this.parent = parent;

			parent.setLayout(new GridLayout(3, false));

			if (targetType != null)
			{
			
				ParsConfig parsConfig = new ParsConfig();

				hashMapComboEnumeration = new HashMap<String, Combo>();
				hashMapComboBoolean = new HashMap<String, Combo>();

				// listParameters = parsConfig.takeParameters(parentNode,
				// sub_Parent);
				// listParameters = parsConfig.takeParameters(path);
				listParameters = parsConfig.takeParameters(targetType);
				hashMapSpinner = new HashMap<String, Spinner>();
				hashMapCheckBox = new HashMap<String, Button>();

				listShortName = new ArrayList<String>();
				if (listParameters != null)
				{
					List<Text> textList = new ArrayList<Text>();
					textList.add(textField);
					for (int i = 0; i < listParameters.size(); i++)
					{
						checkBox = new Button(parent, SWT.CHECK);
						checkBox.setSelection(true);
						label = new Label(parent, SWT.BORDER);
						label.setText(listParameters.get(i).getShortName().toString() + " : ");
						if (listParameters.get(i).getShortName().toString().equals("SHORT-NAME"))
						{
							checkBox.setEnabled(false);
						}
						listShortName.add(listParameters.get(i).getShortName().toString());
						if (listParameters.get(i).getParametersType().toString().contains("ENUMERATION"))
						{

							combo = new Combo(parent, SWT.READ_ONLY);
							combo.setTouchEnabled(false);
							list = new ArrayList<String>();
							for (int j = 0; j < listParameters.get(i).getPrametersValue().size(); j++)
							{
								combo.add(listParameters.get(i).getPrametersValue().get(j).toString());
								list.add(listParameters.get(i).getPrametersValue().get(j).toString());

							}
							combo.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

							hashMapComboEnumeration.put(listParameters.get(i).getShortName().toString(), combo);

						}
						else if (listParameters.get(i).getParametersType().toString().contains("INTEGER"))
						{
							spinner = new Spinner(parent, SWT.BORDER);
							spinner.setMinimum(listParameters.get(i).getMin());
							spinner.setMaximum(listParameters.get(i).getMax());
							spinner.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
							hashMapSpinner.put(listParameters.get(i).getShortName().toString(), spinner);

						}
						else if (listParameters.get(i).getParametersType().toString().contains("BOOLEAN"))
						{

							combo = new Combo(parent, SWT.READ_ONLY);
							combo.setTouchEnabled(false);
							combo.add("True");
							combo.add("False");
							combo.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

							hashMapComboBoolean.put(listParameters.get(i).getShortName().toString(), combo);
						}
						else if (listParameters.get(i).getParametersType().toString().contains("TextField"))
						{

							textField = new Text(parent, SWT.BORDER);
							textField.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

						}

						hashMapCheckBox.put(listParameters.get(i).getShortName().toString(), checkBox);

					}

					button = new Button(parent, SWT.NONE);
					button.addSelectionListener(new SelectionAdapter()
					{
						@SuppressWarnings("rawtypes")
						@Override
						public void widgetSelected(SelectionEvent e)
						{

							dataList = new ArrayList<Data>();
							int ok = 0;

							new HashMap<String, String>();
							new HashMap<String, String>();
							new HashMap<String, String>();

							new HashMap<String, HashMap>();

							for (Entry<String, Button> entry : hashMapCheckBox.entrySet())
							{
								String keyCheckBox = entry.getKey();
								Button checkBoxValue = entry.getValue();

								if (checkBoxValue.getSelection())
								{

									for (Entry<String, Combo> entryBoolean : hashMapComboBoolean.entrySet())
									{
										String key = entryBoolean.getKey();
										Combo comboValue = entryBoolean.getValue();

										if (keyCheckBox.equals(key))
										{
											dataList.add(new Data("Boolean", key, comboValue.getText()));

											if (comboValue.getSelectionIndex() == -1)
											{
												ok++;

											}
										}

									}

									for (Entry<String, Combo> entryEnumeration : hashMapComboEnumeration.entrySet())
									{
										String key = entryEnumeration.getKey();
										Combo comboValue = entryEnumeration.getValue();

										if (keyCheckBox.equals(key))
										{
											dataList.add(new Data("Enumeration", key, comboValue.getText()));
											if (comboValue.getSelectionIndex() == -1)
											{
												ok++;

											}
										}
									}

									for (Entry<String, Spinner> entrySpinner : hashMapSpinner.entrySet())
									{
										String key = entrySpinner.getKey();
										Spinner spinnerValue = entrySpinner.getValue();
										if (keyCheckBox.equals(key))
										{
											dataList.add(new Data("Integer", key, spinnerValue.getText()));
										}
									}

									if (keyCheckBox.equals("SHORT-NAME"))
									{
										dataList.add(new Data("Text", "SHORT-NAME", textField.getText()));
									}

								}

							}

							if ( (textField.getText().length() <= 2))
							{
								//
								System.out.println("ERORARE SAVE");

							}

							if (ok == 0)
							{
								WriteInXML wXML = new WriteInXML();
								if (target.equals(textField.getText()))
									wXML.save(fileName, target, dataList, definitionRef);
								else if (wXML.checkForDuplicate(fileName, textField.getText()) == false)
									wXML.save(fileName, target, dataList, definitionRef);
								
								
								

							}
							else
							{
								System.out.println("EROARE OK = " + ok);
							}

						}

					});
					button.setText("SAVE");

					

				}
			}
//		}

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{

	}

	@Override
	public void setMessage(String message)
	{
		Display.getDefault().syncExec(new Runnable()
		{
			@Override
			public void run()
			{
				// tfShortName.setText(message);
				// tfShortName.pack(true);
			}
		});

	}

	@Override
	public void setPortValue(String portValue)
	{
		Display.getDefault().syncExec(new Runnable()
		{
			@Override
			public void run()
			{
				/*
				 * if (portValue.equals("0")) { comboPinDirection.select(0);
				 * btnPinDirectionChangeable.setSelection(false); } else if
				 * (portValue.equals("1")) { comboPinDirection.select(1);
				 * btnPinDirectionChangeable.setSelection(true); } else if
				 * (portValue.equals("null")) comboPinDirection.select(0);
				 * comboPinDirection.pack(true);
				 */
			}
		});

	}

	@Override
	public void sendHashMap(LinkedHashMap<String, String> hashmap)
	{
		

			for (Entry<String, String> entry1 : hashmap.entrySet())
			{
				String key1 = entry1.getKey();
				String value = entry1.getValue();

				if (key1.trim().equals("SHORT-NAME"))
				{
					textField.setText(value);
				}

				for (Entry<String, Combo> entry : hashMapComboEnumeration.entrySet())
				{
					String key = entry.getKey();
					Combo comboValue = entry.getValue();

					if (key1.equals(key))
					{
						for (int i = 0; i < comboValue.getItemCount(); i++)
						{
							if (comboValue.getItem(i).equals(value))
							{
								comboValue.select(i);
							}
						}
					}
				}

				for (Entry<String, Combo> entry : hashMapComboBoolean.entrySet())
				{
					String key = entry.getKey();
					Combo comboValue = entry.getValue();

					if (key1.equals(key))
					{
						for (int i = 0; i < comboValue.getItemCount(); i++)
						{
							if (value.equals("0"))
							{
								value = "False";
							}
							else if (value.equals("1"))
							{
								value = "True";
							}
							if (comboValue.getItem(i).equals(value))
							{
								comboValue.select(i);
							}
						}
					}
				}

				for (Entry<String, Spinner> entrySpinner : hashMapSpinner.entrySet())
				{
					String keySpinner = entrySpinner.getKey();
					Spinner spinnerValue = entrySpinner.getValue();

					if (key1.equals(keySpinner))
					{
						spinnerValue.setSelection(Integer.parseInt(value));
					}
				}
			}

		

	}

	@Override
	public void sedModel(Model model)
	{

		this.targetType = model.getType();
		this.fileName = model.getFileName();
		this.target = model.getShortName();
		this.definitionRef = model.getDefinitionRef();

		
		
			for (Control control : parent.getChildren())
			{
				control.dispose();
			}

			createPartControl(parent);

			parent.layout();
			parent.layout(true);
		

	}
}
