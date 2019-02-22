```
Fatal Exception: java.lang.NullPointerException
Attempt to invoke virtual method 'android.content.Context android.view.View.getContext()' on a null object reference
com.unnamed.b.atv.model.TreeNode$BaseNodeViewHolder.getView (TreeNode.java:238)
com.unnamed.b.atv.view.AndroidTreeView.addNode (AndroidTreeView.java:249)
com.unnamed.b.atv.view.AndroidTreeView.addNode (AndroidTreeView.java:460)
com.mithrilmania.blocktopograph.nbt.EditorFragment$1$2$3.onClick (EditorFragment.java:940)
```

Then `TreeNode.java@237    final View nodeView = getNodeView();` returned null.

Then `TreeNode.java@258    return createNodeView(mNode, (E) mNode.getValue());` returned null.

and `mNode` must not be null.

It could be from `EditorFragment$NBTNodeHolder` or `EditorFragment$RootNodeHolder`.

TODO: analyse latter.

For earlier, `EditorFragment.java@119`.

It returns null if `chain == null` or `chain.self == null.`

For latter go to Label #chain.self.

For earlier.

The value is always from `TreeNode.java@258    return createNodeView(mNode, (E) mNode.getValue());`.

Then `TreeNode.java@100     public Object getValue()` returned null.

Then `TreeNode.java@44     public TreeNode(Object value)`, `value == null`.

Only possible invoke is `TreeNode.java@34     public static TreeNode root()`.

As the exception happened in `addNode` it should not be a root node.

Discontinued.

TODO: Maybe not so?

% chain.self::

As `self` was only written in `EditorFragment.java@72     public ChainTag(Tag parent, Tag self)`.

In all invokes it's never explicitly set to null.

Discontinued. Needs further information.
